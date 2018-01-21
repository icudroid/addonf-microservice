import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('MeanOfContact e2e test', () => {

    let navBarPage: NavBarPage;
    let meanOfContactDialogPage: MeanOfContactDialogPage;
    let meanOfContactComponentsPage: MeanOfContactComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load MeanOfContacts', () => {
        navBarPage.goToEntity('mean-of-contact');
        meanOfContactComponentsPage = new MeanOfContactComponentsPage();
        expect(meanOfContactComponentsPage.getTitle()).toMatch(/advertiserBoApp.meanOfContact.home.title/);

    });

    it('should load create MeanOfContact dialog', () => {
        meanOfContactComponentsPage.clickOnCreateButton();
        meanOfContactDialogPage = new MeanOfContactDialogPage();
        expect(meanOfContactDialogPage.getModalTitle()).toMatch(/advertiserBoApp.meanOfContact.home.createOrEditLabel/);
        meanOfContactDialogPage.close();
    });

    it('should create and save MeanOfContacts', () => {
        meanOfContactComponentsPage.clickOnCreateButton();
        meanOfContactDialogPage.setValueInput('value');
        expect(meanOfContactDialogPage.getValueInput()).toMatch('value');
        meanOfContactDialogPage.typeSelectLastOption();
        meanOfContactDialogPage.contactSelectLastOption();
        meanOfContactDialogPage.save();
        expect(meanOfContactDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class MeanOfContactComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-mean-of-contact div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class MeanOfContactDialogPage {
    modalTitle = element(by.css('h4#myMeanOfContactLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    valueInput = element(by.css('input#field_value'));
    typeSelect = element(by.css('select#field_type'));
    contactSelect = element(by.css('select#field_contact'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setValueInput = function(value) {
        this.valueInput.sendKeys(value);
    }

    getValueInput = function() {
        return this.valueInput.getAttribute('value');
    }

    setTypeSelect = function(type) {
        this.typeSelect.sendKeys(type);
    }

    getTypeSelect = function() {
        return this.typeSelect.element(by.css('option:checked')).getText();
    }

    typeSelectLastOption = function() {
        this.typeSelect.all(by.tagName('option')).last().click();
    }
    contactSelectLastOption = function() {
        this.contactSelect.all(by.tagName('option')).last().click();
    }

    contactSelectOption = function(option) {
        this.contactSelect.sendKeys(option);
    }

    getContactSelect = function() {
        return this.contactSelect;
    }

    getContactSelectedOption = function() {
        return this.contactSelect.element(by.css('option:checked')).getText();
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
