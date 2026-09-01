class StudentMember extends Member {
    public StudentMember(String memberId, String name, String email) {
        super(memberId, name, email);
    }

    @Override public int getBorrowLimit() { return 3; }
    @Override public double getFinePerDay() { return 2.0; }
    @Override public String getMembershipType() { return "Student"; }
}
