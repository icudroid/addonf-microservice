import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
import * as path from 'path';
describe('FileAttachement e2e test', () => {

    let navBarPage: NavBarPage;
    let fileAttachementDialogPage: FileAttachementDialogPage;
    let fileAttachementComponentsPage: FileAttachementComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load FileAttachements', () => {
        navBarPage.goToEntity('file-attachement');
        fileAttachementComponentsPage = new FileAttachementComponentsPage();
        expect(fileAttachementComponentsPage.getTitle()).toMatch(/advertiserBoApp.fileAttachement.home.title/);

    });

    it('should load create FileAttachement dialog', () => {
        fileAttachementComponentsPage.clickOnCreateButton();
        fileAttachementDialogPage = new FileAttachementDialogPage();
        expect(fileAttachementDialogPage.getModalTitle()).toMatch(/advertiserBoApp.fileAttachement.home.createOrEditLabel/);
        fileAttachementDialogPage.close();
    });

    it('should create and save FileAttachements', () => {
        fileAttachementComponentsPage.clickOnCreateButton();
        fileAttachementDialogPage.setFileInput(absolutePath);
        fileAttachementDialogPage.brandSelectLastOption();
        fileAttachementDialogPage.agencySelectLastOption();
        fileAttachementDialogPage.mediaSelectLastOption();
        fileAttachementDialogPage.save();
        expect(fileAttachementDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class FileAttachementComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-file-attachement div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class FileAttachementDialogPage {
    modalTitle = element(by.css('h4#myFileAttachementLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    fileInput = element(by.css('input#file_file'));
    brandSelect = element(by.css('select#field_brand'));
    agencySelect = element(by.css('select#field_agency'));
    mediaSelect = element(by.css('select#field_media'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setFileInput = function(file) {
        this.fileInput.sendKeys(file);
    }

    getFileInput = function() {
        return this.fileInput.getAttribute('value');
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
