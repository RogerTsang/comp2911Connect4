README.TXT

EVERYDAY/WEEK ROUTINE:

We can do it in GUI client:
1. Sync (pull)
2. Make changes (coding)
3. Make a commit
4. Make Sync (push)

We can do it in Terminal:
1. Go to the project repository 
2. git pull
3. Make changes (coding)
4. git commit
5. Change the commit text
6. git push

If push is NOT allowed (CONFLICT occurs):
* Deal with conflicts carefully:
	1. Find <<<< ==== >>>> (Maybe there are numbers of them)
	2. What it shows is a CONFLICT
	3. We need to MERGE or DELETE code between them CAREFULLY
	4. make a commit again
	5. make a push finally

During making a commit text:
	We should tell what we've done between last commit.
	Therefore, we can backtrack it easily (If one day the program screwed seriously).
