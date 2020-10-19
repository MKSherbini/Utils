from urllib.request import urlopen
from urllib.parse import urlparse
from bs4 import BeautifulSoup
import re
import random
import datetime


pages = set()


def getLinks(pageUrl):
    global pages
    html = urlopen('http://en.wikipedia.org{}'.format(pageUrl))
    bs = BeautifulSoup(html, 'html.parser')
    try:
        print(bs.h1.get_text())
        print(bs.find(id='mw-content-text').find_all('p')[0])
        print(bs.find(id='ca-edit').find('a').attrs['href'])
    except AttributeError:
        print('This page is missing something! Continuing.')

    for link in bs.find_all('a', href=re.compile('^(/wiki/)')):
        if 'href' in link.attrs:
            if link.attrs['href'] not in pages:
                # We have encountered a new page
                newPage = link.attrs['href']
                print('-'*20)
                print(newPage)
                pages.add(newPage)
                getLinks(newPage)


def main():
    startingPage = "http://oreilly.com"
    domain = '{}://{}'.format(urlparse(startingPage).scheme,
                              urlparse(startingPage).netloc)
    includeUrl = '{}://{}'.format(urlparse(domain).scheme,
                                  urlparse(domain).netloc)

    print(urlparse(startingPage).netloc)
    print(domain)
    print(includeUrl)
    # getLinks('')

    # random.seed(datetime.datetime.now())
    # links = getLinks('/wiki/Kevin_Bacon')
    # print(len(links))
    # while len(links) > 0:
    #     newArticle = links[random.randint(0, len(links)-1)].attrs['href']
    #     print(newArticle)
    #     links = getLinks(newArticle)

    # html = urlopen('http://en.wikipedia.org/wiki/Kevin_Bacon')
    # bs = BeautifulSoup(html, 'html.parser')
    # print(len(bs.find('div', {'id': 'bodyContent'}).find_all(
    #     'a', href=re.compile('^(/wiki/)((?!:).)*$'))))
    # print(len(bs.find('div', {'id': 'bodyContent'}).find_all(
    #     'a', href=re.compile('^(/wiki/)'))))

    # i = 0
    # for link in bs.find_all('a'):
    #     if 'href' in link.attrs:
    #         i = i + 1
    #         # print(link.attrs['href'])

    # print(i)

    # for link in bs.find('div', {'id': 'bodyContent'}).find_all(
    #         'a', href=re.compile('^(/wiki/)((?!:).)*$')):
    #     if 'href' in link.attrs:
    #         print(link.attrs['href'])


# Driver Code
if __name__ == '__main__':

    # Calling main() function
    main()
