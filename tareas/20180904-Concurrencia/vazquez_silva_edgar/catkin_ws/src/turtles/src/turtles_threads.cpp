#include "turtles.hpp"
#include <boost/thread/thread.hpp>


int main(int argc, char** argv)
{
  ros::init(argc, argv, "turtles_threads");
  ros::NodeHandle n;
  ros::Rate loop(60.0);

  ros::Subscriber sub_goal_pos = n.subscribe("/turtlesim1/turtle1/pose", 10, goalPoseCallback);
  ros::Subscriber sub_pos_1 = n.subscribe("/turtlesim1/turtle2/pose", 10, currentPoseCallback1);
  ros::Subscriber sub_pos_2 = n.subscribe("/turtlesim1/turtle3/pose", 10, currentPoseCallback2);
  ros::Subscriber sub_pos_3 = n.subscribe("/turtlesim1/turtle4/pose", 10, currentPoseCallback3);
  ros::Subscriber sub_pos_4 = n.subscribe("/turtlesim1/turtle5/pose", 10, currentPoseCallback4);
  ros::Subscriber sub_pos_5 = n.subscribe("/turtlesim1/turtle6/pose", 10, currentPoseCallback5);
  ros::Subscriber sub_pos_6 = n.subscribe("/turtlesim1/turtle7/pose", 10, currentPoseCallback6);
  ros::Subscriber sub_pos_7 = n.subscribe("/turtlesim1/turtle8/pose", 10, currentPoseCallback7);
  ros::Subscriber sub_pos_8 = n.subscribe("/turtlesim1/turtle9/pose", 10, currentPoseCallback8);
  ros::Subscriber sub_pos_9 = n.subscribe("/turtlesim1/turtle10/pose", 10, currentPoseCallback9);
  ros::Subscriber sub_pos_10 = n.subscribe("/turtlesim1/turtle11/pose", 10, currentPoseCallback10);
  ros::Subscriber sub_pos_11 = n.subscribe("/turtlesim1/turtle12/pose", 10, currentPoseCallback11);
  ros::Subscriber sub_pos_12 = n.subscribe("/turtlesim1/turtle13/pose", 10, currentPoseCallback12);
  ros::Subscriber sub_pos_13 = n.subscribe("/turtlesim1/turtle14/pose", 10, currentPoseCallback13);
  ros::Subscriber sub_pos_14 = n.subscribe("/turtlesim1/turtle15/pose", 10, currentPoseCallback14);
  ros::Subscriber sub_pos_15 = n.subscribe("/turtlesim1/turtle16/pose", 10, currentPoseCallback15);
  ros::Subscriber sub_pos_16 = n.subscribe("/turtlesim1/turtle17/pose", 10, currentPoseCallback16);
  ros::Subscriber sub_pos_17 = n.subscribe("/turtlesim1/turtle18/pose", 10, currentPoseCallback17);
  ros::Subscriber sub_pos_18 = n.subscribe("/turtlesim1/turtle19/pose", 10, currentPoseCallback18);
  ros::Subscriber sub_pos_19 = n.subscribe("/turtlesim1/turtle20/pose", 10, currentPoseCallback19);
  ros::Subscriber sub_pos_20 = n.subscribe("/turtlesim1/turtle21/pose", 10, currentPoseCallback20);
  

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

  // Declaracion de hilos
  int rate_b = 10;
  
  boost::thread  t1(controllSignal(), &current_pose[0], &goal_pose, &vel);
  boost::thread  t2(controllSignal(), &current_pose[1], &goal_pose, &vel);
  boost::thread  t3(controllSignal(), &current_pose[2], &goal_pose, &vel);
  boost::thread  t4(controllSignal(), &current_pose[3], &goal_pose, &vel);
  boost::thread  t5(controllSignal(), &current_pose[4], &goal_pose, &vel);
  boost::thread  t6(controllSignal(), &current_pose[5], &goal_pose, &vel);
  boost::thread  t7(controllSignal(), &current_pose[6], &goal_pose, &vel);
  boost::thread  t8(controllSignal(), &current_pose[7], &goal_pose, &vel);
  boost::thread  t9(controllSignal(), &current_pose[8], &goal_pose, &vel);
  boost::thread  t10(controllSignal(), &current_pose[9], &goal_pose, &vel);
  boost::thread  t11(controllSignal(), &current_pose[10], &goal_pose, &vel);
  boost::thread  t12(controllSignal(), &current_pose[11], &goal_pose, &vel);
  boost::thread  t13(controllSignal(), &current_pose[12], &goal_pose, &vel);
  boost::thread  t14(controllSignal(), &current_pose[13], &goal_pose, &vel);
  boost::thread  t15(controllSignal(), &current_pose[14], &goal_pose, &vel);
  boost::thread  t16(controllSignal(), &current_pose[15], &goal_pose, &vel);
  boost::thread  t17(controllSignal(), &current_pose[16], &goal_pose, &vel);
  boost::thread  t18(controllSignal(), &current_pose[17], &goal_pose, &vel);
  boost::thread  t19(controllSignal(), &current_pose[18], &goal_pose, &vel);
  boost::thread  t20(controllSignal(), &current_pose[19], &goal_pose, &vel);


  t1.join();
  t2.join();
  t3.join();
  t4.join();
  t5.join();
  t6.join();
  t7.join();
  t8.join();
  t9.join();
  t10.join();
  t11.join();
  t12.join();
  t13.join();
  t14.join();
  t15.join();
  t16.join();
  t17.join();
  t18.join();
  t19.join();
  t20.join();
  
  Kp = 1.2;
  Kd = 0.4;

  current_pose.resize(20);

  cmd_vel.angular.z = 0.01;
  cmd_vel.linear.x = 2.2;

  while( ros::ok() )
    {
      // This is for turtle objective
      velocity_generator(cmd_vel);
      pub_vel_goal.publish(cmd_vel);

      ros::spinOnce();
      loop.sleep();
    }
  
  
  return 0;
}
