#include <ros/ros.h>
#include <math.h>
#include <turtlesim/Pose.h>
#include <geometry_msgs/Twist.h>

int population_turtles;
float Kp, Kd; 
turtlesim::Pose  goal_pose;
turtlesim::Pose  current_pose_1;


////////////////////////////////////////////////////////
//       callbacks for current pose                   //
void goalPoseCallback(const turtlesim::Pose::ConstPtr& msg)
{
  goal_pose.x = msg->x;
  goal_pose.y = msg->y;
}

void currentPoseCallback1(const turtlesim::Pose::ConstPtr& msg)
{
  current_pose_1.x = msg->x;
  current_pose_1.y = msg->y;
}


void controllSignal(turtlesim::Pose current_pose,
		    turtlesim::Pose goal_pose,
		    geometry_msgs::Twist& vel)
{
  // Error stimation
  float angleError;
  turtlesim::Pose error;
 
  error.x = goal_pose.x - current_pose.x;
  error.y = goal_pose.y - current_pose.y;


  //angleError = atan( double(error.y/error.x) );
  angleError = atan2( error.y, error.x );
  std::cout << "Angle error:  " << angleError;
  
  vel.angular.z = angleError*Kp;
  vel.linear.x = sqrt(error.x*error.x  + error.y*error.y)*Kd;

  std::cout << "   --   Angle correction :  " << vel.angular.z << std::endl;
}


void velocity_generator(geometry_msgs::Twist& vel)
{
  if(vel.angular.z > 4.2)  vel.angular.z = -0.05;
  if(vel.angular.z < -5.2)  vel.angular.z = 0.05;

  if(vel.angular.z > 0.0)  
    vel.angular.z = vel.angular.z + 0.005*(rand() % 50);
  else
    vel.angular.z = vel.angular.z - 0.005*(rand() % 50);
    
  return;
}




int main(int argc, char** argv)
{
  ros::init(argc, argv, "turtles_threads");
  ros::NodeHandle n;
  ros::Rate loop(60.0);

  ros::Subscriber sub_goal_pos = n.subscribe("/turtlesim1/turtle1/pose", 10, goalPoseCallback);
  ros::Subscriber sub_pos_1 = n.subscribe("/turtlesim1/turtle2/pose", 10, currentPoseCallback1);

  ros::Publisher pub_vel_1 = n.advertise<geometry_msgs::Twist>("/turtlesim1/turtle2/cmd_vel", 1);
  ros::Publisher pub_vel_2 = n.advertise<geometry_msgs::Twist>("/turtlesim1/turtle3/cmd_vel", 1);
  ros::Publisher pub_vel_3 = n.advertise<geometry_msgs::Twist>("/turtlesim1/turtle4/cmd_vel", 1);
  ros::Publisher pub_vel_4 = n.advertise<geometry_msgs::Twist>("/turtlesim1/turtle5/cmd_vel", 1);
  ros::Publisher pub_vel_5 = n.advertise<geometry_msgs::Twist>("/turtlesim1/turtle6/cmd_vel", 1);
  ros::Publisher pub_vel_6 = n.advertise<geometry_msgs::Twist>("/turtlesim1/turtle7/cmd_vel", 1);
  ros::Publisher pub_vel_7 = n.advertise<geometry_msgs::Twist>("/turtlesim1/turtle8/cmd_vel", 1);
  ros::Publisher pub_vel_8 = n.advertise<geometry_msgs::Twist>("/turtlesim1/turtle9/cmd_vel", 1);
  ros::Publisher pub_vel_9 = n.advertise<geometry_msgs::Twist>("/turtlesim1/turtle10/cmd_vel", 1);
  ros::Publisher pub_vel_10 = n.advertise<geometry_msgs::Twist>("/turtlesim1/turtle11/cmd_vel", 1);
  ros::Publisher pub_vel_11 = n.advertise<geometry_msgs::Twist>("/turtlesim1/turtle12/cmd_vel", 1);
  ros::Publisher pub_vel_12 = n.advertise<geometry_msgs::Twist>("/turtlesim1/turtle13/cmd_vel", 1);
  ros::Publisher pub_vel_13 = n.advertise<geometry_msgs::Twist>("/turtlesim1/turtle14/cmd_vel", 1);
  ros::Publisher pub_vel_14 = n.advertise<geometry_msgs::Twist>("/turtlesim1/turtle15/cmd_vel", 1);
  ros::Publisher pub_vel_15 = n.advertise<geometry_msgs::Twist>("/turtlesim1/turtle16/cmd_vel", 1);
  ros::Publisher pub_vel_16 = n.advertise<geometry_msgs::Twist>("/turtlesim1/turtle17/cmd_vel", 1);
  ros::Publisher pub_vel_17 = n.advertise<geometry_msgs::Twist>("/turtlesim1/turtle18/cmd_vel", 1);
  ros::Publisher pub_vel_18 = n.advertise<geometry_msgs::Twist>("/turtlesim1/turtle19/cmd_vel", 1);
  ros::Publisher pub_vel_19 = n.advertise<geometry_msgs::Twist>("/turtlesim1/turtle20/cmd_vel", 1);
  ros::Publisher pub_vel_20 = n.advertise<geometry_msgs::Twist>("/turtlesim1/turtle21/cmd_vel", 1);
  ros::Publisher pub_vel_goal = n.advertise<geometry_msgs::Twist>("/turtlesim1/turtle1/cmd_vel", 1);


  geometry_msgs::Twist cmd_vel;
  geometry_msgs::Twist vel_1;

  Kp = 0.6;
  Kd = 0.6;

  cmd_vel.angular.z = 0.01;
  cmd_vel.linear.x = 2.2;

  while( ros::ok() )
    {
      // This is for turtle objective
      velocity_generator(cmd_vel);
      pub_vel_goal.publish(cmd_vel);

      // This is for turtle follower
      controllSignal(current_pose_1, goal_pose, vel_1);
      pub_vel_1.publish(vel_1);

      // std::cout << "Goal_pose:  " << goal_pose << std::endl;
      // std::cout << "Current_pose:  " << current_pose_1 << std::endl;

      ros::spinOnce();
      loop.sleep();
    }
  
  
  return 0;
}
