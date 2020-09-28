// Project1.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "pch.h"

#include <opencv2/opencv.hpp>

#include <iostream>
#include <string>
#include <filesystem>
#include <tchar.h>
#include <windows.h>
#import <msxml6.dll> 

using namespace cv;
using namespace std;

int main(int argc, char** argv)
{
	cv::String loadQuery = "";
	cv::String outputForm = "";
	float dyScale, dxScale;
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
				MSXML2::IXMLDOMNodePtr ext = xmlDoc->selectSingleNode("/Configs/InputExtension");

				sscanf_s((char*)xmlDoc->selectSingleNode("/Configs/OutputDyScale")->Gettext(), "%f", &dyScale);
				sscanf_s((char*)xmlDoc->selectSingleNode("/Configs/OutputDxScale")->Gettext(), "%f", &dxScale);

				outputForm = (char*)xmlDoc->selectSingleNode("/Configs/OutputPath")->Gettext();
				outputForm += "/";
				outputForm += "%s_";
				outputForm += (char*)xmlDoc->selectSingleNode("/Configs/OutputPostfix")->Gettext();
				outputForm += ".";
				outputForm += (char*)xmlDoc->selectSingleNode("/Configs/OutputExtension")->Gettext();

				//printf("Reading From %s\n", (char*)path->Gettext());
				loadQuery = (char*)path->Gettext();
				String sExt = (char*)ext->Gettext();
				printf("Reading All Images From %s With Extention %s\n", loadQuery.c_str(), sExt.c_str());
				loadQuery += "/*." + sExt;
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
	glob(loadQuery, fn, false);

	if (!fn.size()) {
		cout << "Failed to find any image" << endl;
		cin.get(); //wait for a key press
		return -1;
	}


	for (size_t i = 0; i < fn.size(); i++)
	{
		cout << fn[i] << endl;
	}


	int totWidth = 0, totHeight = 0;

	size_t count = fn.size(); //number of png files in images folder
	printf("Scalling All Images with Dx %f and Dy %f\n", dxScale, dyScale);

	for (size_t i = 0; i < count; i++) {
		//Mat tmp(images[i].clone());
		Mat tmp;
		char outCStr[100];
		std::filesystem::path p(fn[i]);

		//cout << p.stem().string() << endl;
		//cout << p.stem()<< endl;

		sprintf_s(outCStr, outputForm.c_str(), p.stem().string().c_str());
		cv::String Output(outCStr);
		resize(imread(fn[i]), tmp, cv::Size(), dxScale, dyScale, 1);
		cout << outputForm << endl;
		cout << outCStr << endl;

		bool isSuccess = imwrite(Output, tmp); //write the image to a file as JPEG 
		//bool isSuccess = imwrite("C:/Users/mh-sh/Desktop/MyImage.jpg", output); //write the image to a file as JPEG 
		//bool isSuccess = imwrite("D:/MyImage.png", image); //write the image to a file as PNG
		if (isSuccess == false)
		{
			cout << "Failed to save an image" << endl;
			cin.get(); //wait for a key press
			return -1;
		}

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
