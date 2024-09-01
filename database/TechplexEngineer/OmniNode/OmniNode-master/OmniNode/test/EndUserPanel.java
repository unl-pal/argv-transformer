/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

//package omninode28;

/**
 *
 * @author student
 *
public class EndUserPanel extends OmniPanel
{
/*
    //private ArrayList<Node> nodelist;
    //private ArrayList<Connect> connectlist;

    private int strlength;
    private JTextField abc;
    private JLabel label;
    private JButton sub;

    DynamicListner DL;

    public EndUserPanel(ArrayList<Connect> cl, ArrayList<Node> nl)
    {
        super(cl, nl);
        //nodelist  = nl;
        //connectlist = cl;
        DL = new DynamicListner();

        
        abc = new JTextField(20);
        //abc.setColumns(20);
        abc.addActionListener(DL);
        label = new JLabel("Enter Your guess");
        label.setForeground(Color.white);
        sub = new JButton ("Submit");
        sub.addActionListener(DL);

        //abc.addActionListener(new ButtonListener());
        add(label);
        add(abc);
        add(sub);
        setBackground (Color.black);
        setPreferredSize (new Dimension(WIDTH, HEIGHT));
        //repaint();
    }


    public void paintComponent(Graphics page)
    {
        super.paintComponent(page);
        //System.out.println("Printing");
        page.setColor(Color.green);
        Point drawBox;
        page.setColor(Color.white);
        for (int i = 0; i<connectlist.size(); i++) //draws white connecting lines
        {   //x.y.w.h
            //connectlist.get(i).update(); // Updates the point that the connection believes to be the center of the box
            // Draws a line between the two center points of the boxes
            if(connectlist.get(i).isVisible()) //??? here?
                page.drawLine((int)(connectlist.get(i).getP1().getX()),(int)(connectlist.get(i).getP1().getY()), (int)(connectlist.get(i).getP2().getX()),(int)(connectlist.get(i).getP2().getY()));
        }
        for (int i = 0; i<nodelist.size(); i++) // Draws the boxes and textfro the nodes
        {
           // System.out.println(nodelist.get(i).getName() + " " +nodelist.get(i).getState()/* + " " + nodelist.get(i).isVisible()*);

            //x.y.w.h
            //System.out.println(nodelist.size());
            //System.out.println(nodelist.get(i).getName() + " " +nodelist.get(i).getState() + " " + nodelist.get(i).isVisible());
            /*if(nodelist.get(i).isDrawn())
            {
                //System.out.println("here");
                page.setColor(Color.green);  // TODO allow user to mod colors
                strlength = nodelist.get(i).getName().length();
                drawBox = nodelist.get(i).getPoint();
                page.fillRect(drawBox.x - 10, drawBox.y - 10, (strlength*7)+10, 25);// Math done for the sixe of the box
                page.setColor(Color.black);
                if(nodelist.get(i).isGuessed())
                {
                    //System.out.println("yea i'm guessed" + nodelist.get(i).getName() + " " + i);
                    page.drawString(nodelist.get(i).getName(), drawBox.x - 3, drawBox.y+7);// Draws the text on top of the box
                }
                else if(nodelist.get(i).isVisible())  //@todo fixed? colerative updates? oh shit
                {
                    //System.out.println("Yea i'm visible " + nodelist.get(i).getName() );
                    String str = "";
                    for (int a=0; a < strlength; a++)
                    {
                        str += " ";
                    }
                    page.drawString(str, drawBox.x - 3, drawBox.y+7);
                }
             }//*//*
        }
    }



    /*
     *
     *
    public void saveprogress(ArrayList<Node> nodelist, ArrayList<Connect> connectlist, String filename)
    {
        //save the node based on its status varible
    }
    public void loadprogress(ArrayList<Node> nodelist, ArrayList<Connect> connectlist, String filename)
    {
        //
    }
    // Load method inherited from OmniPanel*/

//}
