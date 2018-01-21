import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('CustomerTarget e2e test', () => {

    let navBarPage: NavBarPage;
    let customerTargetDialogPage: CustomerTargetDialogPage;
    let customerTargetComponentsPage: CustomerTargetComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load CustomerTargets', () => {
        navBarPage.goToEntity('customer-target');
        customerTargetComponentsPage = new CustomerTargetComponentsPage();
        expect(customerTargetComponentsPage.getTitle()).toMatch(/advertiserBoApp.customerTarget.home.title/);

    });

    it('should load create CustomerTarget dialog', () => {
        customerTargetComponentsPage.clickOnCreateButton();
        customerTargetDialogPage = new CustomerTargetDialogPage();
        expect(customerTargetDialogPage.getModalTitle()).toMatch(/advertiserBoApp.customerTarget.home.createOrEditLabel/);
        customerTargetDialogPage.close();
    });

    it('should create and save CustomerTargets', () => {
        customerTargetComponentsPage.clickOnCreateButton();
        customerTargetDialogPage.sexSelectLastOption();
        customerTargetDialogPage.ageSelectLastOption();
        customerTargetDialogPage.brandSelectLastOption();
        customerTargetDialogPage.save();
        expect(customerTargetDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class CustomerTargetComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-customer-target div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class CustomerTargetDialogPage {
    modalTitle = element(by.css('h4#myCustomerTargetLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    sexSelect = element(by.css('select#field_sex'));
    ageSelect = element(by.css('select#field_age'));
    brandSelect = element(by.css('select#field_brand'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setSexSelect = function(sex) {
        this.sexSelect.sendKeys(sex);
    }

    getSexSelect = function() {
        return this.sexSelect.element(by.css('option:checked')).getText();
    }

    sexSelectLastOption = function() {
        this.sexSelect.all(by.tagName('option')).last().click();
    }
    setAgeSelect = function(age) {
        this.ageSelect.sendKeys(age);
    }

    getAgeSelect = function() {
        return this.ageSelect.element(by.css('option:checked')).getText();
    }

    ageSelectLastOption = function() {
        this.ageSelect.all(by.tagName('option')).last().click();
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
