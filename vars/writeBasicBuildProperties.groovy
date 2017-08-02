#!/usr/bin/groovy

def call(config) {
  config.property_file_name = config.property_file_name ?: 'build.properties'
  config.version = config.version ?: env.VERSION ?: env.BUILD_ID
  
  sh """
    echo VERSION=${config.version} > ${config.property_file_name}
    echo JOB_DISPLAY_URL=${RUN_DISPLAY_URL} >>${config.property_file_name}
    echo BUILD_ID=${BUILD_ID} >> ${config.property_file_name}
    echo BUILD_DATE=`date -d "today" +"%Y%m%d%H%M"` >> ${config.property_file_name}
    echo \"SHA=`git rev-parse --verify HEAD`\" >> ${config.property_file_name}
  """
}
