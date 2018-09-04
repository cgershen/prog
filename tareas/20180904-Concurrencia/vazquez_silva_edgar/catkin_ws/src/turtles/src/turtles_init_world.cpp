#include <ros/ros.h>
#include <turtlesim/Spawn.h>

int initial_population;

int main(int argc, char** argv)
{
  // ROS DECLARATIONS //
  ros::init(argc, argv, "turtles_init_world");
  ros::NodeHandle n;
  ros::Rate loop(10.0);

  // ********************  //
  // Service declarations  //
  ros::service::waitForService("/turtlesim1/spawn");
  ros::ServiceClient srv_add_turtle = n.serviceClient<turtlesim::Spawn>("/turtlesim1/spawn");

  
  initial_population = 20;
  ros::Duration(5.0).sleep();
  
  for(int i = 0; i < initial_population; i++)
  {
    turtlesim::Spawn srv;
    srv.request.x = rand() % 12;
    srv.request.y = rand() % 12;
    srv.request.theta = rand() % 3;
      
    srv_add_turtle.call(srv);
    loop.sleep();

  }


  return 0;
}
