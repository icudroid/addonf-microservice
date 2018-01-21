import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Ad e2e test', () => {

    let navBarPage: NavBarPage;
    let adDialogPage: AdDialogPage;
    let adComponentsPage: AdComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Ads', () => {
        navBarPage.goToEntity('ad');
        adComponentsPage = new AdComponentsPage();
        expect(adComponentsPage.getTitle()).toMatch(/adminBoApp.ad.home.title/);

    });

    it('should load create Ad dialog', () => {
        adComponentsPage.clickOnCreateButton();
        adDialogPage = new AdDialogPage();
        expect(adDialogPage.getModalTitle()).toMatch(/adminBoApp.ad.home.createOrEditLabel/);
        adDialogPage.close();
    });

    it('should create and save Ads', () => {
        adComponentsPage.clickOnCreateButton();
        adDialogPage.setDurationInput('5');
        expect(adDialogPage.getDurationInput()).toMatch('5');
        adDialogPage.statusSelectLastOption();
        adDialogPage.setAdfileIdInput('5');
        expect(adDialogPage.getAdfileIdInput()).toMatch('5');
        adDialogPage.adCampaingSelectLastOption();
        adDialogPage.save();
        expect(adDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class AdComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-ad div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class AdDialogPage {
    modalTitle = element(by.css('h4#myAdLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    durationInput = element(by.css('input#field_duration'));
    statusSelect = element(by.css('select#field_status'));
    adfileIdInput = element(by.css('input#field_adfileId'));
    adCampaingSelect = element(by.css('select#field_adCampaing'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setDurationInput = function(duration) {
        this.durationInput.sendKeys(duration);
    }

    getDurationInput = function() {
        return this.durationInput.getAttribute('value');
    }

    setStatusSelect = function(status) {
        this.statusSelect.sendKeys(status);
    }

    getStatusSelect = function() {
        return this.statusSelect.element(by.css('option:checked')).getText();
    }

    statusSelectLastOption = function() {
        this.statusSelect.all(by.tagName('option')).last().click();
    }
    setAdfileIdInput = function(adfileId) {
        this.adfileIdInput.sendKeys(adfileId);
    }

    getAdfileIdInput = function() {
        return this.adfileIdInput.getAttribute('value');
    }

    adCampaingSelectLastOption = function() {
        this.adCampaingSelect.all(by.tagName('option')).last().click();
    }

    adCampaingSelectOption = function(option) {
        this.adCampaingSelect.sendKeys(option);
    }

    getAdCampaingSelect = function() {
        return this.adCampaingSelect;
    }

    getAdCampaingSelectedOption = function() {
        return this.adCampaingSelect.element(by.css('option:checked')).getText();
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
