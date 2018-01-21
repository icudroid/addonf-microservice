import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
import * as path from 'path';
describe('Media e2e test', () => {

    let navBarPage: NavBarPage;
    let mediaDialogPage: MediaDialogPage;
    let mediaComponentsPage: MediaComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Media', () => {
        navBarPage.goToEntity('media');
        mediaComponentsPage = new MediaComponentsPage();
        expect(mediaComponentsPage.getTitle()).toMatch(/advertiserBoApp.media.home.title/);

    });

    it('should load create Media dialog', () => {
        mediaComponentsPage.clickOnCreateButton();
        mediaDialogPage = new MediaDialogPage();
        expect(mediaDialogPage.getModalTitle()).toMatch(/advertiserBoApp.media.home.createOrEditLabel/);
        mediaDialogPage.close();
    });

    it('should create and save Media', () => {
        mediaComponentsPage.clickOnCreateButton();
        mediaDialogPage.setNameInput('name');
        expect(mediaDialogPage.getNameInput()).toMatch('name');
        mediaDialogPage.setPassPhraseInput('passPhrase');
        expect(mediaDialogPage.getPassPhraseInput()).toMatch('passPhrase');
        mediaDialogPage.setExtIdInput('extId');
        expect(mediaDialogPage.getExtIdInput()).toMatch('extId');
        mediaDialogPage.setSiretInput('siret');
        expect(mediaDialogPage.getSiretInput()).toMatch('siret');
        mediaDialogPage.setSirenInput('siren');
        expect(mediaDialogPage.getSirenInput()).toMatch('siren');
        mediaDialogPage.legalStatusSelectLastOption();
        mediaDialogPage.setLogoInput(absolutePath);
        mediaDialogPage.save();
        expect(mediaDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class MediaComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-media div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class MediaDialogPage {
    modalTitle = element(by.css('h4#myMediaLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    passPhraseInput = element(by.css('input#field_passPhrase'));
    extIdInput = element(by.css('input#field_extId'));
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

    setPassPhraseInput = function(passPhrase) {
        this.passPhraseInput.sendKeys(passPhrase);
    }

    getPassPhraseInput = function() {
        return this.passPhraseInput.getAttribute('value');
    }

    setExtIdInput = function(extId) {
        this.extIdInput.sendKeys(extId);
    }

    getExtIdInput = function() {
        return this.extIdInput.getAttribute('value');
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
