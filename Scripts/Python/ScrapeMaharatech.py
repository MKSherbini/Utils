import requests
from selenium import webdriver
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.common.by import By
from selenium.common.exceptions import TimeoutException

import os
from inspect import getsourcefile
import sys
import time


def main():
    if len(sys.argv) == 1:
        dst = os.path.dirname(os.path.abspath(getsourcefile(lambda: None)))
    else:
        dst = sys.argv[1]

    startingPage = "https://maharatech.gov.eg/login/index.php"
    SiteUser = "x"
    SitePass = "x"
    PATH = "C:/DefinitelyNotWindows/Tracks/Web Scraping/Tools/geckodriver.exe"
    delay = 3
    driver = webdriver.Firefox(executable_path=PATH)
    driver.get(startingPage)
    print(driver.title)

    elemUser = None
    elemPass = None
    try:
        elemUser = WebDriverWait(driver, delay).until(
            EC.presence_of_element_located((By.ID, 'username')))
        elemPass = WebDriverWait(driver, delay).until(
            EC.presence_of_element_located((By.ID, 'password')))
        print("Page is ready!")
    except TimeoutException:
        print("Loading took too much time!")

    print(elemUser.text)
    driver.execute_script(
        "arguments[0].setAttribute('value', arguments[1])", elemUser, SiteUser)
    driver.execute_script(
        "arguments[0].setAttribute('value', arguments[1])", elemPass, SitePass)
    driver.find_element_by_id("loginbtn").click()
    print(driver.title)

    driver.get("https://maharatech.gov.eg/mod/hvp/view.php?id=7134")
    print(driver.title)

    # time.sleep(5)

    # h5pIframe = driver.find_element_by_class_name("h5p-iframe")
    # driver.switch_to.frame(h5pIframe)
    # ytIframe = driver.find_element_by_id("h5p-youtube-0")
    # driver.switch_to.frame(ytIframe)
    # linkEl = driver.find_element_by_css_selector("a[class='ytp-title-link yt-uix-sessionlink']")
    # linkEl = driver.find_element_by_class_name("ytp-title-link yt-uix-sessionlink")
    # print(linkEl.get_attribute("href"))
    # elAs = driver.find_elements_by_tag_name("a")
    # for elA in elAs:
    #     print(elA.get_attribute("id"), elA.get_attribute(
    #         "class"), elA.get_attribute("href"))

    # driver.switch_to.default_content()
    # ytLink = ytIframe.get_attribute("src").split("?")[0]
    # print(ytLink)

    allLinks = []
    while True:
        try:
            # fetch link
            h5pIframe = WebDriverWait(driver, delay).until(
                EC.element_to_be_clickable((By.CLASS_NAME, "h5p-iframe")))
            print(h5pIframe)
            driver.switch_to.frame(h5pIframe)

            ytIframe = WebDriverWait(driver, delay).until(
                EC.element_to_be_clickable((By.ID, "h5p-youtube-0")))
            print(ytIframe)
            driver.switch_to.frame(ytIframe)

            ytLinkElem = WebDriverWait(driver, delay).until(
                EC.element_to_be_clickable((By.CSS_SELECTOR, "a[class='ytp-title-link yt-uix-sessionlink']")))
            print(ytLinkElem)
            ytLink = ytLinkElem.get_attribute("href")
            print(ytLink)
            allLinks.append(ytLink)
            driver.switch_to.default_content()

            elemNext = WebDriverWait(driver, delay).until(
                EC.element_to_be_clickable((By.ID, 'next-activity-link')))
            elemNext.click()
            WebDriverWait(driver, delay).until(
                lambda dirver: driver.execute_script(
                    "return document.readyState") == ("complete")
            )
            time.sleep(5)
            # print("Page is ready!")
        except TimeoutException:
            print("Loading took too much time!")
            break
        except Exception:
            print("fk it")
            break

    with open(os.path.join(dst, "batch-file.txt"), "w", encoding="utf-8") as f:
        for li in allLinks:
            print(li, file=f)
    # driver.quit()


# Driver Code
if __name__ == '__main__':

    # Calling main() function
    main()
