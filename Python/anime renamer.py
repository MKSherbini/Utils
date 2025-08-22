
# Pythono3 code to rename multiple  
# files in a directory or folder 
  
# importing os module 
import os 
  
# Function to rename multiple files 
def main(): 
    i = 0
    dir1="E:\\Tensei shitara Slime Datta Ken\\gogoanime1\\"
    dir2="E:\\Tensei shitara Slime Datta Ken\\"
    for filename in os.listdir(dir1): 
        src =dir1 + filename 
        dst =dir2 + filename[filename.find("Tensei"):]
        
        # rename() function will 
        # rename all the files 
        os.rename(src, dst) 
        print(filename)      
  
# Driver Code 
if __name__ == '__main__': 
      
    # Calling main() function 
    main() 
