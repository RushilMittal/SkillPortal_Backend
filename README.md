// First time Git user
git init

git config --global http.sslVerify false 

git remote add origin https://gitlab.blr.teksystems.com/interns2018/skill-portal

git checkout -b dev

git pull origin dev

git add .

git commit -m "test"

git push origin dev

//EXISTING USER
// if existing git, dont forget to pull code daily and resolve the conflicts if any.

git pull origin dev

// if there are conflicts then resolve them 
// do your changes..

git add .

git commit -m "Your Message"

git push origin dev

-b = branch
-m = commit message
. = all

hello everyone
Don't forget to commit Before merging(pulling)
