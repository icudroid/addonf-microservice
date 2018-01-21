import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
import * as path from 'path';
describe('Brand e2e test', () => {

    let navBarPage: NavBarPage;
    let brandDialogPage: BrandDialogPage;
    let brandComponentsPage: BrandComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Brands', () => {
        navBarPage.goToEntity('brand');
        brandComponentsPage = new BrandComponentsPage();
        expect(brandComponentsPage.getTitle()).toMatch(/adminBoApp.brand.home.title/);

    });

    it('should load create Brand dialog', () => {
        brandComponentsPage.clickOnCreateButton();
        brandDialogPage = new BrandDialogPage();
        expect(brandDialogPage.getModalTitle()).toMatch(/adminBoApp.brand.home.createOrEditLabel/);
        brandDialogPage.close();
    });

    it('should create and save Brands', () => {
        brandComponentsPage.clickOnCreateButton();
        brandDialogPage.setNameInput('name');
        expect(brandDialogPage.getNameInput()).toMatch('name');
        brandDialogPage.setUserIdInput('5');
        expect(brandDialogPage.getUserIdInput()).toMatch('5');
        brandDialogPage.setSiretInput('siret');
        expect(brandDialogPage.getSiretInput()).toMatch('siret');
        brandDialogPage.setSirenInput('siren');
        expect(brandDialogPage.getSirenInput()).toMatch('siren');
        brandDialogPage.legalStatusSelectLastOption();
        brandDialogPage.setLogoInput(absolutePath);
        brandDialogPage.sectorSelectLastOption();
        brandDialogPage.save();
        expect(brandDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class BrandComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-brand div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class BrandDialogPage {
    modalTitle = element(by.css('h4#myBrandLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    userIdInput = element(by.css('input#field_userId'));
    siretInput = element(by.css('input#field_siret'));
    sirenInput = element(by.css('input#field_siren'));
    legalStatusSelect = element(by.css('select#field_legalStatus'));
    logoInput = element(by.css('input#file_logo'));
    sectorSelect = element(by.css('select#field_sector'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    }

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
    }

    setUserIdInput = function(userId) {
        this.userIdInput.sendKeys(userId);
    }

    getUserIdInput = function() {
        return this.userIdInput.getAttribute('value');
    }

    setSiretInput = function(siret) {
        this.siretInput.sendKeys(siret);
    }

    getSiretInput = function() {
        return this.siretInput.getAttribute('value');
    }

    setSirenInput = function(siren) {
        this.sirenInput.sendKeys(siren);
    }

    getSirenInput = function() {
        return this.sirenInput.getAttribute('value');
    }

    setLegalStatusSelect = function(legalStatus) {
        this.legalStatusSelect.sendKeys(legalStatus);
    }

    getLegalStatusSelect = function() {
        return this.legalStatusSelect.element(by.css('option:checked')).getText();
    }

    legalStatusSelectLastOption = function() {
        this.legalStatusSelect.all(by.tagName('option')).last().click();
    }
    setLogoInput = function(logo) {
        this.logoInput.sendKeys(logo);
    }

    getLogoInput = function() {
        return this.logoInput.getAttribute('value');
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
