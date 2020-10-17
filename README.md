# RMIT SEPT 2020 Major Project

# Group wed-18-30-5

## Members

- Kennedy, Neil (s3561388)
- Sulakshana, Moditha (s3756843)
- Yao, Boyan (s3694150)
- Anning, Michael (s3720132)

## Records

- Github repository : https://github.com/RMIT-SEPT/majorproject-wed-18-30-5.git
- ClickUp Workspace : https://app.clickup.com/6915699/home/landing

## Branches

- Master(origin) : stable main branch, dont push too, only pull request from development branch, this pull request will only be done by NeilK
- Development : development branch, pull request from personal branches to add new features in development, don't confirm the merge just create the pull request the merge will be approved by someone else probably Neilk(may be unstable build)
- Personal : personal branch, push all your iterative changes to your own branch, once feature is complete, create a pull request for merging into development.

- Pull from development to get latest code from all users(may be broken)
- Pull from master(origin) to get the last stable build(if the build is broken)
- [Pull request movie](/docs/PullRequest_example720.mov) in `docs` folder

###### Local git setup

- Mac steps(adjust accordingly for win users)
- Open the project root directory in terminal
- Type git remote -v and you should see the following
  - development https://github.com/RMIT-SEPT/majorproject-wed-18-30-5.git (fetch)
  - development https://github.com/RMIT-SEPT/majorproject-wed-18-30-5.git (push)
- If you dont see that but instead say Origin or master, then remove that by typing git remote remove \*name
- Now add development by typing: git remote add development https://github.com/RMIT-SEPT/majorproject-wed-18-30-5.git
- Once that is succesfully we need to set up your local branch
- Type git branch, if your local branch is not your own remote repository name then create one, example: git branch neilk
- Make this your active branch by typing: git checkout neilk
- Remove any other local branches that you may have by typing example: git branch -D neilk (type git branch --help if you want details on cmds)
- Now you are good to go, you can get latest data from development by typing: git fetch --all
- Once the fetch has completed you can merge into your local branch by typing: git merge development/development (merges with local branch)
- You cannot merge if you have changes that are uncommited either commit them OR use git stash , merge then git pop
- Do a git fetch/merge regularly, ALWAYS before pushingto your remote repository.

## Code documentation

[Quick Start](/docs/README.md) in `docs` folder

Moment used for the calender component
npm i input-moment --save

I installed it using customised command npm i --save react-moment, which just got added in package.json file but lib was not there in node_modules, so to do so I execute
npm install --save moment react-moment
and now it is working as expected.

\$ npm install react-router

npm install broswer-router
