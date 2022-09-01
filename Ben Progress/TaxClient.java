//SENG4500
//TaxClient.java by Austin Baxter
//c3356468
import java.io.*;
import java.util.*;
import java.net.*;

class TaxClient
{
    public static void main(String[] args) throws UnknownHostException, IOException
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input ip"); //I.P Address input
        String ip = scanner.nextLine();
        System.out.println("Input port"); //port number input
        int port = scanner.nextInt();
        System.out.println("Insert Keyword to proceed");

        try 
        {
            Socket socket = new Socket(ip, port); //connect to specified port
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream())); //server to client communication
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true); //client to server communication
            String query; //used to store command strings
            while (true) //main program loop
            {
                query = scanner.next(); //user inputs command
                output.println(query); //command sent to server
                if("STORE".equals(query)) //store command procedures
                {
                    output.println(scanner.next()); //lower range input
                    output.println(scanner.next()); //upper range input
                    output.println(scanner.next()); //base tax input
                    output.println(scanner.next()); //tax per dollar input (main tax)
                    String servermessage = input.readLine(); //read server response
                    System.out.println(servermessage); //print ok response to client

                }
                else if("QUERY".equals(query)) //query command procedures
                {
                    String servermessage = input.readLine(); //server response outside of loop, incase no data is found
                    while ("QUERY: OK".equals(servermessage) == false) //loop continues till all tax bracket data is displayed
                    {
                        System.out.println(servermessage); //print data to client
                         servermessage = input.readLine(); //collect next data
                    }
                    System.out.println(servermessage); //print ok response to client
                }
                else if("BYE".equals(query) || "END".equals(query) ) //connection shutdown procedures
                {
                    String servermessage = input.readLine(); //collect acknowledgement of socket shutdown
                    System.out.println(servermessage); //print resonse to client
                    break; //break main program loop
                }
                else //client entered an amount to process tax amount
                {
                    String servermessage = input.readLine(); //collect tax data
                    System.out.println(servermessage); //print response to client
                }


                
            }
            
            
            input.close(); //socket and supporting materials closed
            output.close();
            socket.close();
            scanner.close();
        }
        catch (Exception e) //server connection failed
        {
            System.out.println("Server connection failed");
        }
        

    }




}