def gitBranch(String repoUrl){
	script{
	git '${repoUrl}'
	}
          
}
