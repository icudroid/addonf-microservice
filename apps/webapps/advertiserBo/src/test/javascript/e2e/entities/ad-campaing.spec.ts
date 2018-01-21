import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('AdCampaing e2e test', () => {

    let navBarPage: NavBarPage;
    let adCampaingDialogPage: AdCampaingDialogPage;
    let adCampaingComponentsPage: AdCampaingComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load AdCampaings', () => {
        navBarPage.goToEntity('ad-campaing');
        adCampaingComponentsPage = new AdCampaingComponentsPage();
        expect(adCampaingComponentsPage.getTitle()).toMatch(/advertiserBoApp.adCampaing.home.title/);

    });

    it('should load create AdCampaing dialog', () => {
        adCampaingComponentsPage.clickOnCreateButton();
        adCampaingDialogPage = new AdCampaingDialogPage();
        expect(adCampaingDialogPage.getModalTitle()).toMatch(/advertiserBoApp.adCampaing.home.createOrEditLabel/);
        adCampaingDialogPage.close();
    });

    it('should create and save AdCampaings', () => {
        adCampaingComponentsPage.clickOnCreateButton();
        adCampaingDialogPage.setNameInput('name');
        expect(adCampaingDialogPage.getNameInput()).toMatch('name');
        adCampaingDialogPage.setInitialAmountInput('5');
        expect(adCampaingDialogPage.getInitialAmountInput()).toMatch('5');
        adCampaingDialogPage.setStartInput(12310020012301);
        expect(adCampaingDialogPage.getStartInput()).toMatch('2001-12-31T02:30');
        adCampaingDialogPage.setEndInput(12310020012301);
        expect(adCampaingDialogPage.getEndInput()).toMatch('2001-12-31T02:30');
        adCampaingDialogPage.brandSelectLastOption();
        adCampaingDialogPage.sectorSelectLastOption();
        adCampaingDialogPage.providedBySelectLastOption();
        adCampaingDialogPage.save();
        expect(adCampaingDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class AdCampaingComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-ad-campaing div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class AdCampaingDialogPage {
    modalTitle = element(by.css('h4#myAdCampaingLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    initialAmountInput = element(by.css('input#field_initialAmount'));
    startInput = element(by.css('input#field_start'));
    endInput = element(by.css('input#field_end'));
    brandSelect = element(by.css('select#field_brand'));
    sectorSelect = element(by.css('select#field_sector'));
    providedBySelect = element(by.css('select#field_providedBy'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    }

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
    }

    setInitialAmountInput = function(initialAmount) {
        this.initialAmountInput.sendKeys(initialAmount);
    }

    getInitialAmountInput = function() {
        return this.initialAmountInput.getAttribute('value');
    }

    setStartInput = function(start) {
        this.startInput.sendKeys(start);
    }

    getStartInput = function() {
        return this.startInput.getAttribute('value');
    }

    setEndInput = function(end) {
        this.endInput.sendKeys(end);
    }

    getEndInput = function() {
        return this.endInput.getAttribute('value');
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

    sectorSelectLastOption = function() {
        this.sectorSelect.all(by.tagName('option')).last().click();
    }

    sectorSelectOption = function(option) {
        this.sectorSelect.sendKeys(option);
    }

    getSectorSelect = function() {
        return this.sectorSelect;
    }

    getSectorSelectedOption = function() {
        return this.sectorSelect.element(by.css('option:checked')).getText();
    }

    providedBySelectLastOption = function() {
        this.providedBySelect.all(by.tagName('option')).last().click();
    }

    providedBySelectOption = function(option) {
        this.providedBySelect.sendKeys(option);
    }

    getProvidedBySelect = function() {
        return this.providedBySelect;
    }

    getProvidedBySelectedOption = function() {
        return this.providedBySelect.element(by.css('option:checked')).getText();
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
