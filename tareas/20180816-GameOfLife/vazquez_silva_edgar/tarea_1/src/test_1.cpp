//#####################################
// ####### -LifeGame program-  ########
// #######  By Edgar Vazquez  #########

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <opencv2/opencv.hpp>
#include <opencv2/imgproc.hpp>
#include <opencv2/imgcodecs.hpp>
#include <opencv2/videoio.hpp>
#include <opencv2/highgui.hpp>


//using namespace cv;

int width  = 100;
int height = 100;
int value  = 0;


cv::Mat imageCurrent(height, width, CV_8UC1, cv::Scalar(0));
cv::Mat imageNext(height, width, CV_8UC1, cv::Scalar(0));


void printHelp()
{
  std::cout << "##########################################"  << std::endl;
  std::cout << "##     PROGRAMA DEL JUEGO DE LA VIDA    ##" << std::endl;
  std::cout << "#######  (By Edgar Vazquez) ######" << std::endl << std::endl << std::endl;
  std::cout << "Desarrollado en c++ con librerias de OpenCV 3.1" << std::endl << std::endl;
  std::cout << "  Para interactuar con el programa "  << std::endl;
  std::cout << "  utiliza las teclas del teclado:  "     << std::endl;
  std::cout << "    -i-  Para inicializar la poblacion aletoriamente" << std::endl;
  std::cout << "    -e-  Para limpiar la población total" << std::endl;
  std::cout << "    -r-  Para iterar la población" << std::endl;
  std::cout << "   mouse (click derecho) para poner celulas en cualquier lugar" << std::endl;
  std::cout << "   (ctrl + c)   Para detener el programa en cualquier instante" << std::endl;
  std::cout << std::endl << std::endl  << std::endl;
  
}


void eraseMat()
{
  imageCurrent = cv::Mat::zeros(height, width, CV_8UC1);
}


void initializeMat()
{
  // Function that initialize Mat with a specific density
  float density = 0.35;
  int   density_mat = int(imageCurrent.rows*imageCurrent.cols*density);

  imageCurrent = cv::Mat::zeros(height, width, CV_8UC1);
  
  for(int i=0; i<density_mat; i++)
    {
      //Random number
      int index_i = rand()%100 ;
      int index_j = rand()%100 ;
      if( imageCurrent.at<uchar>(cv::Point(index_i, index_j)) != 255 )
	imageCurrent.at<uchar>(cv::Point(index_i, index_j )) = 255;
      else
	i--;
    }
}


void updateMat()
{
  int whitePoints = 0;
  int neighbors   = 0;

  //Verify connectivity
  for(int j = 0; j < imageCurrent.rows; j++)
    for(int i = 0; i < imageCurrent.cols; i++)
      {
	int neighbors   = 0;
	//Verify 8-connect points
	if((i-1) > 0 && imageCurrent.at<uchar>(cv::Point(i-1 ,  j )) == 255 )
	  neighbors++;
	if((j-1) > 0 && imageCurrent.at<uchar>(cv::Point( i  , j-1)) == 255 )
	  neighbors++;
	if((i+1) < 100 && imageCurrent.at<uchar>(cv::Point(i+1 , j  )) == 255 )
	  neighbors++;
	if((i-1) > 0 && (j+1) < 100 && imageCurrent.at<uchar>(cv::Point(i-1 , j+1)) == 255 )
	  neighbors++;
	if((i-1) > 0 && (j-1) > 0 && imageCurrent.at<uchar>(cv::Point(i-1 , j-1)) == 255 )
	  neighbors++;
	if((i+1) < 100 && (j+1) < 100 && imageCurrent.at<uchar>(cv::Point(i+1 , j+1)) == 255 )
	  neighbors++;
	if((j+1) < 100 && imageCurrent.at<uchar>(cv::Point( i  , j+1)) == 255 )
	  neighbors++;
	if((i+1) < 100 && (j-1) > 0 && imageCurrent.at<uchar>(cv::Point(i+1 , j-1)) == 255 )
	  neighbors++;

	// std::cout << "Neighbors("<< i << "," << j << "):  "
	// 	      << neighbors << std::endl;
	    

	// Verify if the cell is alive
	if( imageCurrent.at<uchar>(cv::Point(i,j)) == 255 )
	  {
	    if(neighbors >= 2 && neighbors < 4)
	      imageNext.at<uchar>(cv::Point(i,j)) = 255;  //Survival
	    else
	      imageNext.at<uchar>(cv::Point(i,j)) = 0;
	    
	    whitePoints++;
	  }
	else if( imageCurrent.at<uchar>(cv::Point(i,j)) == 0 )
	  {
	    if(neighbors == 3)
	      {
		//std::cout << "Birth" << std::endl;
		imageNext.at<uchar>(cv::Point(i,j)) = 255; //Birth
	      }
	  }
      }
  
  imageNext.copyTo(imageCurrent);
  // cv::imshow("Netx state", imageNext);
  // std::cout << "Ponts:  " << whitePoints << std::endl;
	
}


//Callback for mouse event
void onMouse(int event, int x, int y, int, void*)
{
  // std::cout << "x,y:  " << x << " " << y << std::endl;

  if (event != cv::EVENT_LBUTTONDOWN)
    return;

  //std::cout << "Event click button" << std::endl;
  std::cout << "click in position:  " << x << "  " << y << std::endl;

  imageCurrent.at<uchar>(cv::Point(x,y)) = 255;
  
  cv::imshow("Life Game", imageCurrent);
  
}


void growPopulation()
{
  int count = 0;
  while( cv::waitKey(50) != 27 && count < 500)
    {
      updateMat();
      cv::imshow("Life Game", imageCurrent);
      //cv::imshow("Next state", imageNext);
      count++;
    }
  
}





int main(int argc, char** argv )
{
  printHelp();

  ////// Create windows //////
  cv::namedWindow("Life Game", cv::WINDOW_NORMAL);

  ///// Set events  /////////
  cv::setMouseCallback("Life Game", onMouse, 0);
  
  while( cv::waitKey(5) != 27)
    {
      if(cv::waitKey(5) == 'u')
	updateMat();

      if(cv::waitKey(5) == 'i')
	initializeMat();
      
      if(cv::waitKey(5) == 'e')
	eraseMat();

      if(cv::waitKey(5) == 'r')
	growPopulation();
      
      cv::imshow("Life Game", imageCurrent);

    }
  
  return 0;
}
