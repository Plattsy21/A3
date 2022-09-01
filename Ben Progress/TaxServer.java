//SENG4500
//TaxServer.java by Austin Baxter
//c3356468
import java.io.*;
import java.util.*;
import java.net.*;
class TaxServer
{
     
    public static void main(String[] args) throws IOException
    {
        int low; //the lowest range of the new tax bracket, being added to memory
        int high; //the highest range of the new tax bracket
        int base; //the base tax that is a one off payment for all amounts that are in the new bracket
        int main; //the main tax of the bracket, that accumulates for each dollar present in the new bracket
        boolean flag; //used to check if this bracket has no upper limit
        int amount; //used to store the amount that is being calculated in each tax bracket
        ArrayList<TaxStore> memory = new ArrayList<TaxStore>(); //memory that stores all the tax brackets on the server
        TaxStore taxStore = null; //initialises taxStore

       // int port = Integer.parseInt(args[0]); //port input by server host
       int port = 50000;
       ServerSocket serverSocket = new ServerSocket(port); //serversocket connected to port
       System.out.println("CONNECTION OPEN on port "+port+""); 
       Socket socket = serverSocket.accept(); //wait for client to connect
       BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream())); //client to server communication
       String clientmessage = "";
       PrintWriter output = new PrintWriter(socket.getOutputStream(), true); //server to client communication
       clientmessage = input.readLine(); //read client message, checking for TAX command
       while ("TAX".equals(clientmessage) == false) //Client did not input TAX command
       {
            output.println("TAX: FAIL"); 
                input.close();
                output.close();
                socket.close(); //close connection
                socket = serverSocket.accept(); //wait for new client to connect
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(socket.getOutputStream(), true);
                clientmessage = input.readLine(); 
       }
       output.println("TAX: OK"); 
       
       boolean inserted = false; //used to prevent the same tax bracket being inserted twice
    while (true) //main program loop
        {
            
            clientmessage = input.readLine();
            
            System.out.println(clientmessage);
            
             

             if ("STORE".equals(clientmessage))
            {
                if(memory.size() == 10)
                {
                    output.println("STORE: FULL"); 
                }
                clientmessage = input.readLine();
                low = Integer.parseInt(clientmessage);
                clientmessage = input.readLine();
                if( clientmessage.indexOf("~") != -1)
                {
                    flag = true;
                    high = -1;
                }
                else
                {
                    flag = false;
                    high = Integer.parseInt(clientmessage);
                }

                 clientmessage = input.readLine();
                 base = Integer.parseInt(clientmessage);
                 clientmessage = input.readLine();
                 main = Integer.parseInt(clientmessage);
                taxStore = new TaxStore(low, high, base, main, flag);
                
                inserted = false;
                boolean recheck = false;
                for (int x = 0; x < memory.size(); x++)
                {   
                    if (taxStore.getLowRange() > memory.get(x).getLowRange() &&  memory.get(x).getMaxFlag() == true)
                    {
                        memory.get(x).setHighRange(taxStore.getLowRange());
                        memory.get(x).setMaxFlag(false);
                        if(inserted == false)
                            {
                                System.out.println("1 ADDED");  
                                memory.add(x+1, taxStore); 
                                inserted = true;
                            }
                    }
                    if (taxStore.getLowRange() < memory.get(x).getLowRange() && taxStore.getHighRange() < memory.get(x).getLowRange())
                    {
                        System.out.println("HI IM 1");  
                        if(inserted == false)
                            {
                                System.out.println("1 ADDED");  
                                memory.add(x, taxStore); 
                                inserted = true;
                            }
                    }


                    else if (taxStore.getLowRange() == memory.get(x).getLowRange() && taxStore.getHighRange() > memory.get(x).getHighRange() )
                    {
                        System.out.println("REMOVAL");
                        memory.remove(x);
                        x--;
                    }

                    else if (taxStore.getLowRange() > memory.get(x).getLowRange() && taxStore.getLowRange() < memory.get(x).getHighRange() )
                    {
                        if(taxStore.getHighRange() < memory.get(x).getHighRange()) //new taxStore inserted in middle of previous range
                        {
                            System.out.println("HI IM 2");  
                            TaxStore taxStore2 = new TaxStore(taxStore.getHighRange() + 1, memory.get(x).getHighRange(), memory.get(x).getBaseTax(), memory.get(x).getMainTax(), memory.get(x).getMaxFlag());
                            memory.get(x).setHighRange(taxStore.getLowRange() - 1);
                            memory.add(x+1, taxStore2);
                            if(inserted == false)
                            {
                                System.out.println("2 ADDED");  
                                memory.add(x+1, taxStore); 
                                inserted = true;
                            }
                        
                            
                        }
                        else
                        {
                            System.out.println("HI IM 3");  
                            memory.get(x).setHighRange(taxStore.getLowRange() - 1);
                            memory.get(x+1).setLowRange(taxStore.getHighRange()); 
                            if(inserted == false)
                            {
                                System.out.println("3 ADDED");  
                                memory.add(x+1,taxStore);
                                inserted = true;
                            }
                            
                        }

                    }
                    else if(taxStore.getLowRange() < memory.get(x).getLowRange() && taxStore.getHighRange() > memory.get(x).getHighRange())
                    {
                        System.out.println("HI IM 4");  
                        if(inserted == true)
                        {
                            System.out.println("HI IM 4 REMOVED");  
                            memory.remove(x);
                            x--;
                        }
                        else
                        {
                            System.out.println("4 ADDED");  
                            memory.set(x,taxStore);
                            inserted = true;  
                            x--;
                        }
                       
                    } 

                    else if(taxStore.getLowRange() < memory.get(x).getLowRange() && taxStore.getHighRange() < memory.get(x).getHighRange() && taxStore.getHighRange() > memory.get(x).getLowRange())
                    {
                        System.out.println("HI IM 5");  
                        memory.get(x).setLowRange(taxStore.getHighRange() + 1);
                        if(inserted == false)
                        {
                            System.out.println("5 ADDED");  
                            memory.add(x,taxStore);
                            inserted = true;  
                        }
                        
                    }
                    else if (taxStore.getLowRange() == memory.get(x).getLowRange() && taxStore.getHighRange() == memory.get(x).getHighRange())
                    {
                        System.out.println("HI IM 6");  
                        if(inserted == false)
                        {
                            System.out.println("6 ADDED");  
                            memory.set(x,taxStore);
                            inserted = true;  
                        }
                        
                    }
                    else if(taxStore.getLowRange() < memory.get(x).getLowRange() && taxStore.getHighRange() == memory.get(x).getHighRange())
                    {
                        System.out.println("HI IM 7");  
                    }
                    else if(taxStore.getLowRange() == memory.get(x).getLowRange() && taxStore.getHighRange() < memory.get(x).getHighRange())
                    {
                        System.out.println("HI IM NEW");
                        memory.get(x).setLowRange(taxStore.getHighRange()+ 1);
                        if(inserted == false)
                        {
                            System.out.println("NEW ADDED");  
                            memory.add(x,taxStore);
                            inserted = true;  
                        }
                    }
                    else if(taxStore.getLowRange() == memory.get(x).getHighRange())
                    {
                        memory.get(x).setHighRange(taxStore.getLowRange() - 1);
                        if(inserted == false)
                        {
                            System.out.println("NEW ADDED");  
                            memory.add(x+1,taxStore);
                            inserted = true;  
                        }
                    }


                }
                if (inserted == false)
                {
                    System.out.println("HI IM 8 DEFAULT");  
                    memory.add(taxStore);
                    inserted = true;
                }
                
                
                
                output.println("STORE: OK");
                
            }

             else if ("QUERY".equals(clientmessage))
            {

                for(int x = 0; x < memory.size(); x++)
                {
                    output.println(memory.get(x).valueReturn());
                }
                    output.println("QUERY: OK");
                
                

            }

            

           else if ("BYE".equals(clientmessage))
            {
                output.println("BYE: OK");
                input.close();
                output.close();
                socket.close();
                socket = serverSocket.accept();
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 output = new PrintWriter(socket.getOutputStream(), true);
                 clientmessage = input.readLine();
                    while ("TAX".equals(clientmessage) == false)
                    {
                        input.close();
                        output.close();
                        socket.close();
                        socket = serverSocket.accept();
                        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        output = new PrintWriter(socket.getOutputStream(), true);
                        clientmessage = input.readLine();
                    }
                    output.println("TAX: OK"); 
            }

        else if ("END".equals(clientmessage))
            {
                output.println("END: OK");
                input.close();
                output.close();
                socket.close();
                break;
            }
       else if (clientmessage.matches("\\d+"))
        {
            double taxedAmount = 0;
            amount = Integer.parseInt(clientmessage);
           for (int x = 0; x < memory.size(); x++)
           {
               taxedAmount += memory.get(x).calculate(amount);
           }

            output.println("TAX IS "+String.format("%.2f", taxedAmount));
        }
        else
        {
            output.println("INVALID");  
        }
        

        
        }
       

    
    }





}