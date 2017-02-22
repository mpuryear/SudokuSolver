/***************************
 * Author : Mathew Puryear
 * Date : Spring 2017
 * File : GroupSudokuSolver.c
 * Purpose : Run the SudokuSolver on every board in the ./boards directory and output a rough 
 *           estimation of the total time taken.
 *
 *************************/


#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <dirent.h>
#include <string.h>
#include <time.h>

char  name[256][256];
static const char * dir = "./boards/";

int main() {

  unsigned long startTime = (unsigned long) time(NULL);
  
  DIR * dirp;
  struct dirent * entry;
  int count = 0;

  int parentID = getpid();
  
  dirp = opendir(dir);
  // Loop through all files in our sub folder at the dir path.
  while ((entry = readdir(dirp)) != NULL) {
    if (entry->d_type == DT_REG) { 
      // Append our directory to the filename.
      strcat(name[count], dir);
      strcat(name[count], entry->d_name);
      // Create a fork and run the file.
      if(getpid() == parentID) {
	// The parent creates the forks but does not call execlp itself
	wait(NULL); // Wait until all children have finished to start another fork.
	fork();
      }
      if(getpid() != parentID) {
	// Only forks call exelcp
	// java SudokuSolver <filename>
      execlp("java", "java", "SudokuSolver", name[count], (char*) NULL);
      }
      
      count++;
    }
  }
  closedir(dirp);

  // Force the parent to wait for the final fork to finish.
  wait(NULL);
  unsigned long deltaTime = (unsigned long) time(NULL) - startTime;

  // c's time is only accurate to the second, so this is a rough approximation.
  printf("\nTotal run-time: ~%ds\n", deltaTime);
  
  return 0;
}
