import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('AgencyUser e2e test', () => {

    let navBarPage: NavBarPage;
    let agencyUserDialogPage: AgencyUserDialogPage;
    let agencyUserComponentsPage: AgencyUserComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load AgencyUsers', () => {
        navBarPage.goToEntity('agency-user');
        agencyUserComponentsPage = new AgencyUserComponentsPage();
        expect(agencyUserComponentsPage.getTitle()).toMatch(/advertiserBoApp.agencyUser.home.title/);

    });

    it('should load create AgencyUser dialog', () => {
        agencyUserComponentsPage.clickOnCreateButton();
        agencyUserDialogPage = new AgencyUserDialogPage();
        expect(agencyUserDialogPage.getModalTitle()).toMatch(/advertiserBoApp.agencyUser.home.createOrEditLabel/);
        agencyUserDialogPage.close();
    });

    it('should create and save AgencyUsers', () => {
        agencyUserComponentsPage.clickOnCreateButton();
        agencyUserDialogPage.setUserIdInput('5');
        expect(agencyUserDialogPage.getUserIdInput()).toMatch('5');
        agencyUserDialogPage.agencySelectLastOption();
        agencyUserDialogPage.save();
        expect(agencyUserDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class AgencyUserComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-agency-user div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class AgencyUserDialogPage {
    modalTitle = element(by.css('h4#myAgencyUserLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    userIdInput = element(by.css('input#field_userId'));
    agencySelect = element(by.css('select#field_agency'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setUserIdInput = function(userId) {
        this.userIdInput.sendKeys(userId);
    }

    getUserIdInput = function() {
        return this.userIdInput.getAttribute('value');
    }

    agencySelectLastOption = function() {
        this.agencySelect.all(by.tagName('option')).last().click();
    }

    agencySelectOption = function(option) {
        this.agencySelect.sendKeys(option);
    }

    getAgencySelect = function() {
        return this.agencySelect;
    }

    getAgencySelectedOption = function() {
        return this.agencySelect.element(by.css('option:checked')).getText();
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
