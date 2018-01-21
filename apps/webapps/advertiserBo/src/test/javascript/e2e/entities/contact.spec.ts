import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Contact e2e test', () => {

    let navBarPage: NavBarPage;
    let contactDialogPage: ContactDialogPage;
    let contactComponentsPage: ContactComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Contacts', () => {
        navBarPage.goToEntity('contact');
        contactComponentsPage = new ContactComponentsPage();
        expect(contactComponentsPage.getTitle()).toMatch(/advertiserBoApp.contact.home.title/);

    });

    it('should load create Contact dialog', () => {
        contactComponentsPage.clickOnCreateButton();
        contactDialogPage = new ContactDialogPage();
        expect(contactDialogPage.getModalTitle()).toMatch(/advertiserBoApp.contact.home.createOrEditLabel/);
        contactDialogPage.close();
    });

    it('should create and save Contacts', () => {
        contactComponentsPage.clickOnCreateButton();
        contactDialogPage.setLastnameInput('lastname');
        expect(contactDialogPage.getLastnameInput()).toMatch('lastname');
        contactDialogPage.setFirstnameInput('firstname');
        expect(contactDialogPage.getFirstnameInput()).toMatch('firstname');
        contactDialogPage.brandSelectLastOption();
        contactDialogPage.agencySelectLastOption();
        contactDialogPage.mediaSelectLastOption();
        contactDialogPage.save();
        expect(contactDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ContactComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-contact div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ContactDialogPage {
    modalTitle = element(by.css('h4#myContactLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    lastnameInput = element(by.css('input#field_lastname'));
    firstnameInput = element(by.css('input#field_firstname'));
    brandSelect = element(by.css('select#field_brand'));
    agencySelect = element(by.css('select#field_agency'));
    mediaSelect = element(by.css('select#field_media'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setLastnameInput = function(lastname) {
        this.lastnameInput.sendKeys(lastname);
    }

    getLastnameInput = function() {
        return this.lastnameInput.getAttribute('value');
    }

    setFirstnameInput = function(firstname) {
        this.firstnameInput.sendKeys(firstname);
    }

    getFirstnameInput = function() {
        return this.firstnameInput.getAttribute('value');
    }

    brandSelectLastOption = function() {
        this.brandSelect.all(by.tagName('option')).last().click();
    }

    brandSelectOption = function(option) {
        this.brandSelect.sendKeys(option);
    }

    getBrandSelect = function() {
        return this.brandSelect;
    }

    getBrandSelectedOption = function() {
        return this.brandSelect.element(by.css('option:checked')).getText();
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

    mediaSelectLastOption = function() {
        this.mediaSelect.all(by.tagName('option')).last().click();
    }

    mediaSelectOption = function(option) {
        this.mediaSelect.sendKeys(option);
    }

    getMediaSelect = function() {
        return this.mediaSelect;
    }

    getMediaSelectedOption = function() {
        return this.mediaSelect.element(by.css('option:checked')).getText();
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
