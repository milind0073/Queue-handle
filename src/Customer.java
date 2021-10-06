/**
* Customer Class which holds number of item in cart and return same with serve time
* Milind Joshi, 000788962
* */
public class Customer {

    private int numberItems;                //Object to store Item on cart

    //Constructor
    public Customer(int numberItems) {
        this.numberItems = numberItems;
    }

    //Get method
    public int getNumberItems() {
        return numberItems;
    }

    //set method
    public void setNumberItems(int numberItems) {
        this.numberItems = numberItems;
    }

    //returns servetime
    public int serveTime(){
        return 45+(5*getNumberItems());
    }

    @Override
    public String toString() {
        return "["+getNumberItems()+"("+serveTime()+" s)]";
    }
}
