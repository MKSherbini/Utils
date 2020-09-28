
# Pythono3 code to rename multiple  
# files in a directory or folder 
  
# importing os module 
import os 
  
# Function to rename multiple files 
def main(): 
    i = 0
    dir1="D:\\Tracks\\GameDev\\Apps\\Unity\\Projects\\Basra\\_\\"
    dir2="D:\\Tracks\\GameDev\\Apps\\Unity\\Projects\\Basra\\_\\_\\"
    for filename in os.listdir(dir1): 
        src =dir1  + filename
        print(filename)
        

        if(len(filename.split("_of_"))==2):
            print(filename.split("_of_"))
            if len(filename.split("_of_")[0]) <= 2:
                dst =dir2  + filename.split("_of_")[1][0] + filename.split("_of_")[0]+ ".png"
                os.rename(src, dst)
            elif len(filename.split("_of_")[0]) == 3:
                dst =dir2  + filename.split("_of_")[1][0] + filename.split("_of_")[0][0]+ ".png"
                os.rename(src, dst) 
            elif filename!="_" and filename[-5]=='2':
                dst =dir2  + filename.split("_of_")[1][0] + filename.split("_of_")[0][0]+ ".png"
                os.rename(src, dst) 
        # rename() function will 
        # rename all the files 
        
        #print(filename.split("_of_"))
        
       
# Driver Code 
if __name__ == '__main__': 
      
    # Calling main() function 
    main() 
