package pt.ipp.isep.dei.lapr2.pot.model;

public enum EnumFreelancerExpertise {
    JUNIOR(1, "Junior"),
    SENIOR(2, "Senior");

    private int extraCostPerHour;
    private String designation;

    EnumFreelancerExpertise(int i, String designation) {
        this.extraCostPerHour = i;
        this.designation = designation;
    }

    public int getExtraCostPerHour() {
        return extraCostPerHour;
    }

    public String getDesignation() {
        return designation;
    }

    public static EnumFreelancerExpertise returnEnumByDesignation(String designation){
      for (EnumFreelancerExpertise f : EnumFreelancerExpertise.values()){
          if (f.designation.equalsIgnoreCase(designation)){
              return f;
          }
      }
      return null;
    }

    public double computeAmount(double paymentAmount){
        return paymentAmount*extraCostPerHour;
    }
}
