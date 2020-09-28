// Project1.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#pragma comment(lib, "shlwapi.lib")
#include "pch.h"

#include <opencv2/opencv.hpp>

#include <iostream>
#include <string>

#include <tchar.h>
#include <windows.h>

#include <shlwapi.h>

#import <msxml6.dll> 

using namespace cv;
using namespace std;

int main(int argc, char** argv)
{
	//cv::String a="10";
	//cout << StrCmpLogicalW(L"2",std::wstring(a.begin(), a.end()).c_str()) << endl;
	//cout << StrCmpLogicalW(L"2",L"10") << endl;

	cv::String loadQuery = "";
	cv::String outputForm = "";
	vector<cv::String> loadExts;

	HRESULT hr = CoInitialize(NULL);
	if (SUCCEEDED(hr)) {
		try {
			MSXML2::IXMLDOMDocument2Ptr xmlDoc;
			hr = xmlDoc.CreateInstance(__uuidof(MSXML2::DOMDocument60),
				NULL, CLSCTX_INPROC_SERVER);
			// TODO: if (FAILED(hr))...

			if (xmlDoc->load(_T("input.xml")) != VARIANT_TRUE) {
				printf("Unable to load input.xml\n");
			}
			else {
				printf("XML was successfully loaded\n");

				//MSXML2::IXMLDOMElementPtr spRoot = xmlDoc->documentElement; //root node

				xmlDoc->setProperty("SelectionLanguage", "XPath");
				MSXML2::IXMLDOMNodePtr path = xmlDoc->selectSingleNode("/Configs/InputPath");
				MSXML2::IXMLDOMNodeListPtr extensions = xmlDoc->selectNodes("/Configs/InputExtensions/*");


				outputForm = (char*)xmlDoc->selectSingleNode("/Configs/OutputPath")->Gettext();
				outputForm += "/";
				outputForm += (char*)xmlDoc->selectSingleNode("/Configs/OutputName")->Gettext();
				outputForm += ".";
				outputForm += (char*)xmlDoc->selectSingleNode("/Configs/OutputExtension")->Gettext();

				//printf("Reading From %s\n", (char*)path->Gettext());
				loadQuery = (char*)path->Gettext();
				printf("Reading All Images From %s With Extentions: ", loadQuery.c_str());
				for (size_t i = 0; i < extensions->length; i++)
				{
					String sExt = (char*)extensions->item[i]->Gettext();
					cout << sExt << " ";
					loadExts.push_back(sExt);
				}
				cout << endl;
				//loadQuery += "/*." + sExt;
			}
		}
		catch (_com_error & e) {
			printf("ERROR: %ws\n", e.ErrorMessage());
		}
		CoUninitialize();
	}

	//// Read the image file
	//Mat image = imread("C:/Users/mh-sh/Pictures/vlcsnap-2019-11-28-17h56m45s197.png");

	//if (image.empty()) // Check for failure
	//{
	//	cout << "Could not open or find the image" << endl;
	//	system("pause"); //wait for any key press
	//	return -1;
	//}

	vector<cv::String> fn;
	for (size_t i = 0; i < loadExts.size(); i++)
	{
		vector<cv::String> t;
		glob(loadQuery + "/*." + loadExts[i], t, false);
		fn.insert(fn.end(), t.begin(), t.end());
	}

	sort(fn.begin(), fn.end(), [](string& a, string& b) { return StrCmpLogicalW(std::wstring(a.begin(), a.end()).c_str(), std::wstring(b.begin(), b.end()).c_str())<0; });

	if (!fn.size()) {
		cout << "Failed to find any image" << endl;
		cin.get(); //wait for a key press
		return -1;
	}
	/*int cnt = 0, start = 2;
	while (cnt < 5) {
		string tmp = fn[start];
		fn.erase(fn.begin() + start);
		cnt++;
		fn.push_back(tmp);
	}*/

	for (size_t i = 0; i < fn.size(); i++)
	{
		cout << fn[i] << endl;
	}

	int totWidth = 0, totHeight = 0;
	vector<Mat> images;
	size_t count = fn.size(); //number of png files in images folder

	images.push_back(imread(fn[0]));

	for (size_t i = 1; i < count; i++) {
		//Mat tmp(images[0].clone());
		Mat tmp = imread(fn[i]);

		//resize(imread(fn[i]), tmp, tmp.size(), 0, 0, 1);

		resize(tmp, tmp, cv::Size(images[0].size().width, tmp.size().height * images[0].size().width / tmp.size().width), 0, 0, 1);
		images.push_back(tmp);
	}

	Mat output;
	vconcat(images, output);

	bool isSuccess = imwrite(outputForm, output); //write the image to a file as JPEG 
	//bool isSuccess = imwrite("C:/Users/mh-sh/Desktop/MyImage.jpg", output); //write the image to a file as JPEG 
	//bool isSuccess = imwrite("D:/MyImage.png", image); //write the image to a file as PNG
	if (isSuccess == false)
	{
		cout << "Failed to save the image" << endl;
		cin.get(); //wait for a key press
		return -1;
	}


	//String windowName = "My HelloWorld Window"; //Name of the window

	//namedWindow(windowName); // Create a window

	//imshow(windowName, images[0]); // Show our image inside the created window.

	cout << "Success" << endl;
	waitKey(0); // Wait for any keystroke in the window

	//destroyWindow(windowName); //destroy the created window

	return 0;
}
// Run program: Ctrl + F5 or Debug > Start Without Debugging menu
// Debug program: F5 or Debug > Start Debugging menu

// Tips for Getting Started: 
//   1. Use the Solution Explorer window to add/manage files
//   2. Use the Team Explorer window to connect to source control
//   3. Use the Output window to see build output and other messages
//   4. Use the Error List window to view errors
//   5. Go to Project > Add New Item to create new code files, or Project > Add Existing Item to add existing code files to the project
//   6. In the future, to open this project again, go to File > Open > Project and select the .sln file
