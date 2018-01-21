import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
import * as path from 'path';
describe('Agency e2e test', () => {

    let navBarPage: NavBarPage;
    let agencyDialogPage: AgencyDialogPage;
    let agencyComponentsPage: AgencyComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Agencies', () => {
        navBarPage.goToEntity('agency');
        agencyComponentsPage = new AgencyComponentsPage();
        expect(agencyComponentsPage.getTitle()).toMatch(/advertiserBoApp.agency.home.title/);

    });

    it('should load create Agency dialog', () => {
        agencyComponentsPage.clickOnCreateButton();
        agencyDialogPage = new AgencyDialogPage();
        expect(agencyDialogPage.getModalTitle()).toMatch(/advertiserBoApp.agency.home.createOrEditLabel/);
        agencyDialogPage.close();
    });

    it('should create and save Agencies', () => {
        agencyComponentsPage.clickOnCreateButton();
        agencyDialogPage.setNameInput('name');
        expect(agencyDialogPage.getNameInput()).toMatch('name');
        agencyDialogPage.setSiretInput('siret');
        expect(agencyDialogPage.getSiretInput()).toMatch('siret');
        agencyDialogPage.setSirenInput('siren');
        expect(agencyDialogPage.getSirenInput()).toMatch('siren');
        agencyDialogPage.legalStatusSelectLastOption();
        agencyDialogPage.setLogoInput(absolutePath);
        agencyDialogPage.save();
        expect(agencyDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class AgencyComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-agency div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class AgencyDialogPage {
    modalTitle = element(by.css('h4#myAgencyLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    siretInput = element(by.css('input#field_siret'));
    sirenInput = element(by.css('input#field_siren'));
    legalStatusSelect = element(by.css('select#field_legalStatus'));
    logoInput = element(by.css('input#file_logo'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    }

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
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
