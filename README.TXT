README.TXT

A better way to integrate eclipse and github:
	https://www.youtube.com/watch?v=ptK9-CNms98
	
	You may need:
	http://download.eclipse.org/egit/updates
	https://github.com/RogerTsang/comp2911Connect4.git
	
	*When import, there is an error said .project already exists.
	Skipped it, click cancel.
	
	When everything is done. 
	You should right click your project in Eclipse and there should be team submenu.
	Use it to pull/add/commit/push as Github client.

EVERYDAY/WEEK ROUTINE:
We can do it in GUI client:
	1. Sync (pull)
	2. Make changes (coding)
	3. Add files to index if new files are created.
	4. Make a commit
	5. Make Sync (push)

We can do it in Terminal:
	1. Go to the project repository 
	2. "git pull"
	3. Make changes (coding)
	4. "git add <filename>" (if new a file is created)
	5. "git commit"
	6. Change the commit text
	7. "git push"

If push is NOT permitted (CONFLICT occurs):
* Deal with conflicts carefully:
	1. Find <<<< ==== >>>> (Maybe there are numbers of them)
	2. What it shows is a CONFLICT
	3. We need to MERGE or DELETE code between them CAREFULLY (manually)
	4. Make a commit again
	5. Make a push finally

During making a commit text:
	We should tell what we've done between last commit.
	Therefore, we can backtrack it easily (If one day the program screwed seriously).