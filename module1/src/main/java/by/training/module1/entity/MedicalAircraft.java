package by.training.module1.entity;

public class MedicalAircraft extends Aircraft {
    private boolean resuscitationEquipment;
    private int firstAidKits;

    public MedicalAircraft(String model, int passengers, int fuelConsumption, double loadCapacity, int speed,
                           boolean resuscitationEquipment, int firstAidKits) {
        super(AircraftType.MEDICAL, model, passengers, fuelConsumption, loadCapacity, speed);
        this.resuscitationEquipment = resuscitationEquipment;
        this.firstAidKits = firstAidKits;
    }

    public boolean isResuscitationEquipment() {
        return resuscitationEquipment;
    }

    public void setResuscitationEquipment(boolean resuscitationEquipment) {
        this.resuscitationEquipment = resuscitationEquipment;
    }

    public int getFirstAidKits() {
        return firstAidKits;
    }

    public void setFirstAidKitNum(int firstAidKits) {
        this.firstAidKits = firstAidKits;
    }
}
