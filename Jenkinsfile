node {
   def mvnHome
   stage('Preparation') { // for display purposes
      // Get some code from a GitHub repository
      git 'https://github.com/SkIoT-Cloud/trails-service.git'
      // Get the Maven tool.
      // ** NOTE: This 'M3' Maven tool must be configured
      // **       in the global configuration.
      mvnHome = tool 'mvn'
   }
   stage('Build') {
      sh "'${mvnHome}/bin/mvn' clean package"
   }
}