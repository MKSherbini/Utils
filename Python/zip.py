
# Pythono3 code to rename multiple  
# files in a directory or folder 
  
# importing os module 
import os 
from zipfile import ZipFile

# Function to rename multiple files 
def main(): 
    i = 0
    file1="C:/Users/mh-sh/Desktop/obadTrash/trashObad.mp4"
    dir1="C:/Users/mh-sh/Desktop/obadTrash"
    zOut="C:/Users/mh-sh/Desktop/obadTrash/trashObad.zip"

    for i in range(100): 
        if i%10==0:
            print(i)
            
        #     # create a ZipFile object
        zipObj = ZipFile(zOut, 'w')
        # Add multiple files to the zip
        zipObj.write(file1,"trashObad.mp4") 
        # zipObj.write(dir1+"/"+file1[3:]) 
            # close the Zip File
        zipObj.close()
        os.remove(file1)

        # zipFilesInDir(dir1, zOut, lambda name : 'mp4' in name)
        # Create a ZipFile Object and load sample.zip in it
        with ZipFile(zOut, 'r') as zipObj:
        # Extract all the contents of zip file in different directory
            zipObj.extractall(dir1)

        os.remove(zOut)
    
      
# Zip the files from given directory that matches the filter
def zipFilesInDir(dirName, zipFileName, filter):
# create a ZipFile object
    with ZipFile(zipFileName, 'w') as zipObj:
    # Iterate over all the files in directory
        for folderName, subfolders, filenames in os.walk(dirName):
            for filename in filenames:
                if filter(filename):
                # create complete filepath of file in directory
                    filePath = os.path.join(folderName, filename)
                    # Add file to zip
                    zipObj.write(filePath)  

# Driver Code 
if __name__ == '__main__': 
      
    # Calling main() function 
    main() 
