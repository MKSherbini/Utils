package main

import (
	"fmt"
	"github.com/go-rod/rod"
	"github.com/go-rod/rod/lib/launcher"
	"time"
)

const (
	TELEGRAM_APITOKEN = "xxx"
)

func UNUSED(x ...interface{}) {}

func main() {
	// span label
	// couponTextpctch2640186058078945
	// couponTextpctch5379374979599922 badgepctch37701607723184105
	// promoMessagepctch48897713945966437 greenBadgepctch48897713945966437
	// promoMessageCXCWpctch09945488002006908 greenBadgepctch09945488002006908
	// $("#badgepctch3873136513275468").parentElement.children[3]
	//
	// https://www.amazon.eg/-/en/Cottonil-long-boxer-shorts-Fitted/dp/B096L83ZWZ

	// Launch a new browser with default options, and connect to it.
	l := launcher.New().
		Headless(false).
		Devtools(true)

	defer l.Cleanup()

	url := l.MustLaunch()

	browser := rod.New().
		ControlURL(url).
		Trace(true).
		SlowMotion(2 * time.Second).
		MustConnect()
	//browser := rod.New().MustConnect()

	//launcher.Open(browser.ServeMonitor(""))
	// Even you forget to close, rod will close it after main process ends.
	defer browser.MustClose()

	// Create a new page
	page := browser.MustPage("https://www.amazon.eg/-/en/Cottonil-long-boxer-shorts-Fitted/dp/B096L83ZWZ").MustWaitStable()

	//text, err := page.MustElementR("label", "[Bb]adgepctch").Text()
	text, err := page.MustElement("#productTitle").Text()

	fmt.Println(err)
	fmt.Println(text)
	//// Trigger the search input with hotkey "/"
	//page.Keyboard.MustType(input.Slash)
	//
	//// We use css selector to get the search input element and input "git"
	//page.MustElement("#query-builder-test").MustInput("git").MustType(input.Enter)
	//
	//// Wait until css selector get the element then get the text content of it.
	//text := page.MustElementR("span", "most widely used").MustText()
	//
	//fmt.Println(text)
	//
	//// Get all input elements. Rod supports query elements by css selector, xpath, and regex.
	//// For more detailed usage, check the query_test.go file.
	//fmt.Println("Found", len(page.MustElements("input")), "input elements")
	//
	//// Eval js on the page
	//page.MustEval(`() => console.log("hello world")`)
	//
	//// When eval on an element, "this" in the js is the current DOM element.
	//fmt.Println(page.MustElement("title").MustEval(`() => this.innerText`).String())

	//resp, err := http.Get("https://www.amazon.eg/-/en/Cottonil-long-boxer-shorts-Fitted/dp/B096L83ZWZ")
	//if err != nil {
	//	log.Fatal(err)
	//}
	//
	//if resp.StatusCode != http.StatusOK {
	//	log.Fatal("status code: " + strconv.Itoa(resp.StatusCode))
	//}
	//
	//bodyBytes, err := io.ReadAll(resp.Body)
	//if err != nil {
	//	log.Fatal(err)
	//}
	//
	//fmt.Println(string(bodyBytes))

	//c := colly.NewCollector()
	//wg := sync.WaitGroup{}
	//wg.Add(1)
	//
	//c.OnRequest(func(r *colly.Request) {
	//	fmt.Println("Visiting: ", r.URL)
	//})
	//c.OnError(func(_ *colly.Response, err error) {
	//	wg.Done()
	//	log.Println("Something went wrong: ", err)
	//})
	//c.OnResponse(func(r *colly.Response) {
	//	fmt.Println("Page visited: ", r.Request.URL)
	//	fmt.Println("Page visited: ", string(r.Body))
	//})
	//c.OnHTML("#productTitle", func(element *colly.HTMLElement) {
	//	fmt.Println(element.Text)
	//	wg.Done()
	//})
	//
	//err := c.Visit("https://www.amazon.eg/-/en/Cottonil-long-boxer-shorts-Fitted/dp/B096L83ZWZ")
	//if err != nil {
	//	return
	//}
	//wg.Wait()
	//db, err := gorm.Open(sqlite.Open("test.db"), &gorm.Config{})
	//if err != nil {
	//	panic("failed to connect database")
	//}
	//
	////Migrate the schema
	//db.AutoMigrate(&User{})
	//
	//pref := telebot.Settings{
	//	Token:  TELEGRAM_APITOKEN,
	//	Poller: &telebot.LongPoller{Timeout: 10 * time.Second},
	//}
	//
	//b, err := telebot.NewBot(pref)
	//if err != nil {
	//	log.Fatal(err)
	//	return
	//}
	//
	//b.Handle("/start", func(c telebot.Context) error {
	//	db.Create(NewUser(c.Sender()))
	//	return nil
	//})
	//
	//b.Start()
}

// deleting
//var msgs []telebot.StoredMessage
//db.Find(&msgs) // gorm syntax
//for i := 1; i < 12; i++ {
//	message := telebot.StoredMessage{
//		MessageID: strconv.Itoa(i),
//		ChatID:    704770520,
//	}
//
//	err = b.Delete(message)
//	if err != nil {
//		continue
//	}
//b.DeleteMany()
//}
