package com.scottejames.downsman.model;

import com.scottejames.downsman.services.ScoutService;
import com.scottejames.downsman.services.SupportService;

import java.util.List;


public class TeamModel extends Model {

    public String toString() {
        return "Team : " + teamName + " Hike class " + hikeClass;
    }
    private String leaderName = null;


    private String teamName = null;
    private String hikeClass = null;
    private String section = null;
    private String district = null;
    private String county = null;
    private String prefStart = null;
    private String activeMobile = null;
    private String backupMobile = null;
    private String emergencyContactName = null;
    private String emergencyContactMobile = null;
    private boolean paymentSubmitted = false;
    private boolean paymentRecieved = false;
    private boolean teamSubmitted = false;

    public boolean isPaymentSubmitted() {
        return paymentSubmitted;
    }

    public void setPaymentSubmitted(boolean paymentSubmitted) {
        this.paymentSubmitted = paymentSubmitted;
    }

    public boolean isPaymentRecieved() {
        return paymentRecieved;
    }

    public void setPaymentRecieved(boolean paymentRecieved) {
        this.paymentRecieved = paymentRecieved;
    }

    public boolean isTeamSubmitted() {
        return teamSubmitted;
    }

    public void setTeamSubmitted(boolean teamSubmitted) {
        this.teamSubmitted = teamSubmitted;
    }



   public String getSubmittedStatus(){
       if (teamSubmitted) {
           return "Submitted";
       } else {
           return "Not Submitted";
       }

   }
   public String getPaymentStatus(){
       if (paymentRecieved) {
           return "Paid";
       } else if (paymentSubmitted){
           return "Payment Submitted";
       } else {
           return "Not Paid";
       }
    }


    private final ScoutService scoutService = new ScoutService();
    private final SupportService supportService = new SupportService();

    public String getEmergencyContactLandline() {
        return emergencyContactLandline;
    }

    public void setEmergencyContactLandline(String emergencyContactLandline) {
        this.emergencyContactLandline = emergencyContactLandline;
    }

    private String emergencyContactLandline = null;

    public String getEmergencyContactEmail() {
        return emergencyContactEmail;
    }

    public void setEmergencyContactEmail(String emergencyContactEmail) {
        this.emergencyContactEmail = emergencyContactEmail;
    }

    private String emergencyContactEmail = null;

    public TeamModel(){

    }
    public TeamModel(String teamName) {
        this.teamName = teamName;
    }

    public TeamModel(String teamName, String hikeClass) {
        this.teamName = teamName;
        this.hikeClass = hikeClass;
    }

    public TeamModel(String teamName, String hikeClass, String status, String section, String district, String county, String prefStart, String activeMobile, String backupMobile, String emergencyContactName, String emergencyContactMobile, String emergencyContactLandline) {
        this.teamName = teamName;
        this.hikeClass = hikeClass;
        this.section = section;
        this.district = district;
        this.county = county;
        this.prefStart = prefStart;
        this.activeMobile = activeMobile;
        this.backupMobile = backupMobile;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactMobile = emergencyContactMobile;
        this.emergencyContactLandline = emergencyContactLandline;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getPrefStart() {
        return prefStart;
    }

    public void setPrefStart(String prefStart) {
        this.prefStart = prefStart;
    }

    public String getActiveMobile() {
        return activeMobile;
    }

    public void setActiveMobile(String activeMobile) {
        this.activeMobile = activeMobile;
    }

    public String getBackupMobile() {
        return backupMobile;
    }

    public void setBackupMobile(String backupMobile) {
        this.backupMobile = backupMobile;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactMobile() {
        return emergencyContactMobile;
    }

    public void setEmergencyContactMobile(String emergencyContactMobile) {
        this.emergencyContactMobile = emergencyContactMobile;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getHikeClass() {
        return hikeClass;
    }

    public void setHikeClass(String hikeClass) {
        this.hikeClass = hikeClass;
    }


    public void addScoutMember(ScoutModel scout){
        if (scout.isPersisted())
            scoutService.update(scout);
        else
            scoutService.add(scout);
    }

    public void addSupportMember(SupportModel support){
        if (support.isPersisted())
            supportService.update((support));
        else
            supportService.add((support));

    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public List<ScoutModel> getScoutsTeam(){
        return scoutService.getAll();
    }

    public List<SupportModel> getSupportTeam(){
        return supportService.getAll();
    }

    public void removeScoutMember(ScoutModel scout){
        scoutService.remove(scout);
    }
    public void removeSupportMember(SupportModel support){
        supportService.remove(support);
    }


    public String validateForSubmission() {
        String validation = "";
        if ((getTeamName() == null) || (getTeamName().isEmpty())){
            validation += "Team Name Cant Be Empty\n";
        }
        return validation;
    }
}

