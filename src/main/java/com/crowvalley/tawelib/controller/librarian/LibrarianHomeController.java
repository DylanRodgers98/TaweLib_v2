package com.crowvalley.tawelib.controller.librarian;

public class LibrarianHomeController {

    private LibrarianResourcesTabController resourcesTabController;

    private LibrarianLoansTabController loansTabController;

    private LibrarianUsersTabController usersTabController;

    private LibrarianFinesAndPaymentsTabController finesAndPaymentsTabController;

    private LibrarianProfileTabController profileTabController;

    private LibrarianLogOutController logOutController;

    public void setResourcesTabController(LibrarianResourcesTabController resourcesTabController) {
        this.resourcesTabController = resourcesTabController;
    }

    public void setLoansTabController(LibrarianLoansTabController loansTabController) {
        this.loansTabController = loansTabController;
    }

    public void setUsersTabController(LibrarianUsersTabController usersTabController) {
        this.usersTabController = usersTabController;
    }

    public void setFinesAndPaymentsTabController(LibrarianFinesAndPaymentsTabController finesAndPaymentsTabController) {
        this.finesAndPaymentsTabController = finesAndPaymentsTabController;
    }

    public void setProfileTabController(LibrarianProfileTabController profileTabController) {
        this.profileTabController = profileTabController;
    }

    public void setLogOutController(LibrarianLogOutController logOutController) {
        this.logOutController = logOutController;
    }

}
