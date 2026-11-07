package com.rit.ecopulse.model;

public class EcoHealthResponse {
    private String status = "UP";
    private String service = "ecopulse-aiops";
    private String version = "1.0.0";
    private String studentUsn = "1MS24CI060";
    private String department = "CSE (Artificial Intelligence and Machine Learning)";
    private String institution = "M. S. Ramaiah Institute of Technology";
    private String courseCode = "25CIAEC59";
    private String facultyGuide = "";
    private String activeCarbonRegion = "ap-south-1";
    private double gridCarbonIntensity = 660.0; // gCO2/kWh
    private long uptimeSeconds;

    public EcoHealthResponse() {}

    public EcoHealthResponse(String status, String activeCarbonRegion, double gridCarbonIntensity, long uptimeSeconds) {
        this.status = status;
        this.activeCarbonRegion = activeCarbonRegion;
        this.gridCarbonIntensity = gridCarbonIntensity;
        this.uptimeSeconds = uptimeSeconds;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getService() { return service; }
    public void setService(String service) { this.service = service; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public String getStudentUsn() { return studentUsn; }
    public void setStudentUsn(String studentUsn) { this.studentUsn = studentUsn; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getInstitution() { return institution; }
    public void setInstitution(String institution) { this.institution = institution; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getFacultyGuide() { return facultyGuide; }
    public void setFacultyGuide(String facultyGuide) { this.facultyGuide = facultyGuide; }

    public String getActiveCarbonRegion() { return activeCarbonRegion; }
    public void setActiveCarbonRegion(String activeCarbonRegion) { this.activeCarbonRegion = activeCarbonRegion; }

    public double getGridCarbonIntensity() { return gridCarbonIntensity; }
    public void setGridCarbonIntensity(double gridCarbonIntensity) { this.gridCarbonIntensity = gridCarbonIntensity; }

    public long getUptimeSeconds() { return uptimeSeconds; }
    public void setUptimeSeconds(long uptimeSeconds) { this.uptimeSeconds = uptimeSeconds; }
}
