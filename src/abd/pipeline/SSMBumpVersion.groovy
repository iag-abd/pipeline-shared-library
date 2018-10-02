package abd.pipeline
import com.amazonaws.services.simplesystemsmanagement.*
import com.amazonaws.services.simplesystemsmanagement.model.*
import com.amazonaws.regions.Regions
import com.amazonaws.auth.*

/**
 *  Warning = opinionated and simple
 *  Use this to get a semantic version from an AWS param store.
 *  Assumes very simple semantic style for now x.y.z.
 *  Assumes instance running in AWS with privs to param store (for now).
 *  Assumes env for region set
 *  Assumes jenkins plugin for aws sdk is installed
 *
 * Uses the AWS sdk simplesystemsmanagement ssm Parameter Store for semantic versioning in Jenkins Pipeline Jobs
 *
 * Will create parameter if one does not exist
 * 
 * Static will need to be approved: staticMethod com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder defaultClient
 *
*/

class SSMBumpVersion {

  @NonCPS
  def testWithCreds(paramName, region, id, key) {
    BasicAWSCredentials awsCreds = new BasicAWSCredentials(id, key);

    AWSSimpleSystemsManagement ssmc = AWSSimpleSystemsManagementClientBuilder.standard()
      .withRegion(Regions.AP_SOUTHEAST_2)
      .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
      .build();

    return getVersion(paramName, ssmc)
  }

  @NonCPS
  def getVersion(paramName) {
    AWSSimpleSystemsManagement ssmc = AWSSimpleSystemsManagementClientBuilder.defaultClient();
    return getVersion(paramName, ssmc)
  }

  @NonCPS
  def putParam(paramName, value, overwrite, encryptKey, ssmc) {
    PutParameterRequest putParameterRequest = new PutParameterRequest()
    ///note note set overwrite
    putParameterRequest.setOverwrite(overwrite)
    putParameterRequest.setName(paramName)
    putParameterRequest.setValue(value)
    putParameterRequest.setType(ParameterType.String)
    return ssmc.putParameter(putParameterRequest);
  }

  @NonCPS
  def getVersion(paramName, ssmc) {
    def defaultNewVersion = '0.0.0'
    try
    {
      GetParameterRequest request = new GetParameterRequest()
              .withName(paramName)    
      GetParameterResult result = ssmc.getParameter(request);

      def version = result.getParameter().getValue();
      def matcher = version =~ /(?<major>\d*).(?<minor>\d*).(?<revision>\d*)[.-]*(.*)/
      version = matcher[0][1] + "." + matcher[0][2] + "." + (Integer.parseInt(matcher[0][3]) + 1)
      putParam(paramName, version, true, null, ssmc)
      return version
    } 
    catch(ParameterNotFoundException pnfe)
    {
      putParam(paramName, defaultNewVersion, false, null, ssmc) 
      return defaultNewVersion
    }
  }
}
