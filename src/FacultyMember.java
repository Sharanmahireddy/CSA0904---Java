class FacultyMember extends Member {
    public FacultyMember(String memberId, String name, String email) {
        super(memberId, name, email);
    }

    @Override public int getBorrowLimit() { return 5; }
    @Override public double getFinePerDay() { return 3.0; }
    @Override public String getMembershipType() { return "Faculty"; }
}
