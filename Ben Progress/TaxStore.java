//SENG4500
//TaxStore.java by Austin Baxter
//c3356468
class TaxStore { //Tax Store acts as storage for an individual tax bracket
    private int lowRange; //the lowest range of the tax bracket
    private int highRange; //the highest range of the tax bracket
    private int baseTax; //the base tax that is a one off payment for all amounts that are in this bracket
    private int mainTax; //the main tax of the bracket, that accumulates for each dollar present in the bracket
    private boolean maxFlag; //used to check if this bracket has no upper limit

    public TaxStore(int low, int high, int base, int main, boolean max) { //constructor
        lowRange = low;
        highRange = high;
        baseTax = base;
        mainTax = main;
        maxFlag = max;
    }

    public String valueReturn() { //Returns the data in this tax bracket
        if (maxFlag == true) {
            return lowRange + " ~ " + baseTax + " " + mainTax; //tax brackets with no upper limit are represented with a ~
        } else {
            return lowRange + " " + highRange + " " + baseTax + " " + mainTax;
        }
    }

    public double calculate(int amount) //calculates the tax an amount of money will accumulate for this particular bracket
    {
        double output = 0.00; //instantiate output as a double to accomodate for cents representation
        double mainCalculate = mainTax; //transfers mainTax data to usable, cent format
        mainCalculate = mainCalculate/100.00;

    if (maxFlag == false) //tax bracket has an upper limit
    {
        if (amount > lowRange && amount < highRange) //amount ends in this tax bracket
        {
            output = amount - lowRange; //amount outside of bracket is removed
            output = output * mainCalculate; //tax per dollar calculated
            output = output + baseTax; //base tax added at the end
        }
        else if( amount > lowRange && amount >= highRange) //amount is greater than the tax bracket
        {
            output = highRange - lowRange; //amount not needed for calculation, use highest number in bracket instead
            output = output * mainCalculate; //tax per dollar calculated
            output = output + baseTax; //base tax added at the end
        }
    }
    else if(amount < lowRange) //amount not in tax bracket, no tax paid
    {
        output = 0.00;
    }
    else //tax bracket has no upper limit, since this is represented as -1 in the class, it has its own section
    {
        output = amount - lowRange; //amount outside of bracket is removed
        output = output * mainCalculate; //tax per dollar calculated
        output = output + baseTax; //base tax added at the end
    }
        return output; 
    }

    public int getLowRange() //returns the lowest range of the tax bracket
    {
        return lowRange;
    }

    public void setLowRange(int newRange) //updates the lowest range of the tax bracket
    {
        lowRange = newRange;
    }

    public void setHighRange(int newRange) //updates the highest range of the tax bracket
    {
        highRange = newRange;
    }

    public int getHighRange() //returns the highest range of the tax bracket
    {
        return highRange;
    }

    public void setBaseTax(int newTax) //updates the base one off tax of the tax bracket
    {
        baseTax = newTax;
    }
    public int getBaseTax() //returns the base one off tax of the tax bracket
    {
        return baseTax;
    }

    public void setMainTax(int newTax) //updates the tax per dollar of the tax bracket
    {
        mainTax = newTax;
    }

    public int getMainTax() //returns the tax per dollar of the tax bracket
    {
        return mainTax;
    }

    public void setMaxFlag(boolean newFlag) //updates the flag that checks if the tax bracket has an upper limit
    {
        maxFlag = newFlag;
    }

    public boolean getMaxFlag() //returns the flag that checks if the tax bracket has an upper limit
    {
        return maxFlag;
    }

}