def gitBranch(String repoUrl,String branch){
	git branch: '${branch}',
          url: "${repoUrl}"
}
