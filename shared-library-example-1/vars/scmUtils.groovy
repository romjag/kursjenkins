def gitCheckout(Map stageParams) {

    checkout([
        $class: 'GitSCM',
        branches: [[name:  stageParams.branch ]],
        userRemoteConfigs: [[ url: stageParams.url, credentialsId: stageParams.credentialsId ]],
        extensions: [[$class: 'LocalBranch']]
    ])
}


def setGitUserInfo(String email, String username) {
  sh "git config user.name ${username}"
  sh "git config user.email ${email}"
}

def gitCommit(Map stageParams) {
  sh "git add ${stageParams.path}"
  sh "git commit -m ${stageParams.commitMessage}"
}

def gitPush(Map stageParams) {
    withCredentials([usernamePassword(credentialsId: stageParams.credentialsId, usernameVariable: 'username', passwordVariable: 'password')])
      {
        sh "git push https://$password@github.com/${stageParams.gitHubPath}.git"
      }
}
