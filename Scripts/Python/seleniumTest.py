import requests
from selenium import webdriver


def main():

    PATH = "C:/DefinitelyNotWindows/Tracks/Web Scraping/Tools/geckodriver.exe"

    driver = webdriver.Firefox(executable_path=PATH)
    driver.get("https://www.google.com/")
    print(driver.title)
    driver.quit()
    
    # arr = ["a342z"]
    # response = requests.get("http://api.open-notify.org/astros.json")
    # print(response.status_code)
    # json = response.json()
    # print(json["message"])
    # for idx in range(json["number"]):
    #     print(json["people"][idx]["craft"], json["people"][idx]["name"])
    #     pass


# Driver Code
if __name__ == '__main__':

    # Calling main() function
    main()
