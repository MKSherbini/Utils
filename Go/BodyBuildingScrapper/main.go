package main

import (
	"fmt"
	"github.com/playwright-community/playwright-go"
	"log"
)

func UNUSED(x ...interface{}) {}

func main() {
	// BuffedSkull@gmail.com
	url := "https://www.bodybuilding.com/exercises/finder?muscle=chest"
	err := playwright.Install()
	if err != nil {
		log.Fatalf("could not install playwright: %v", err)
	}
	pw, err := playwright.Run()
	if err != nil {
		log.Fatalf("could not start playwright: %v", err)
	}
	browser, err := pw.Chromium.Launch()
	if err != nil {
		log.Fatalf("could not launch browser: %v", err)
	}
	page, err := browser.NewPage()
	if err != nil {
		log.Fatalf("could not create page: %v", err)
	}
	if _, err = page.Goto(url); err != nil {
		log.Fatalf("could not goto: %v", err)
	}
	fmt.Println(page.Content())
	//entries, err := page.Locator(".athing").All()
	//if err != nil {
	//	log.Fatalf("could not get entries: %v", err)
	//}
	//for i, entry := range entries {
	//	title, err := entry.Locator("td.title > span > a").TextContent()
	//	if err != nil {
	//		log.Fatalf("could not get text content: %v", err)
	//	}
	//	fmt.Printf("%d: %s\n", i+1, title)
	//}

	if err = browser.Close(); err != nil {
		log.Fatalf("could not close browser: %v", err)
	}
	if err = pw.Stop(); err != nil {
		log.Fatalf("could not stop Playwright: %v", err)
	}
}

//func downloadFile(url string) {
//	// Get the file name from the URL
//	fileName := path.Base(url)
//	if !strings.HasSuffix(fileName, ".mp4") && !strings.HasSuffix(fileName, ".webm") {
//		fileName += ".mp4"
//	}
//
//	// Create the file
//	out, err := os.Create(fileName)
//	if err != nil {
//		fmt.Printf("Failed to create file: %v\n", err)
//		return
//	}
//	defer out.Close()
//
//	// Download the video
//	resp, err := http.Get(url)
//	if err != nil {
//		fmt.Printf("Failed to download the video: %v\n", err)
//		return
//	}
//	defer resp.Body.Close()
//
//	// Copy the content to the file
//	_, err = io.Copy(out, resp.Body)
//	if err != nil {
//		fmt.Printf("Failed to save the video: %v\n", err)
//	}
//	fmt.Printf("Downloaded video: %s\n", fileName)
//}
//func checkError(err error) {
//	if err != nil {
//		log.Fatal(err)
//	}
//}
