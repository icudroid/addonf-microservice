import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Permission e2e test', () => {

    let navBarPage: NavBarPage;
    let permissionDialogPage: PermissionDialogPage;
    let permissionComponentsPage: PermissionComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Permissions', () => {
        navBarPage.goToEntity('permission');
        permissionComponentsPage = new PermissionComponentsPage();
        expect(permissionComponentsPage.getTitle()).toMatch(/adminBoApp.permission.home.title/);

    });

    it('should load create Permission dialog', () => {
        permissionComponentsPage.clickOnCreateButton();
        permissionDialogPage = new PermissionDialogPage();
        expect(permissionDialogPage.getModalTitle()).toMatch(/adminBoApp.permission.home.createOrEditLabel/);
        permissionDialogPage.close();
    });

    it('should create and save Permissions', () => {
        permissionComponentsPage.clickOnCreateButton();
        permissionDialogPage.setNameInput('name');
        expect(permissionDialogPage.getNameInput()).toMatch('name');
        permissionDialogPage.setExtentionInput('extention');
        expect(permissionDialogPage.getExtentionInput()).toMatch('extention');
        permissionDialogPage.setDescriptionInput('description');
        expect(permissionDialogPage.getDescriptionInput()).toMatch('description');
        permissionDialogPage.save();
        expect(permissionDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class PermissionComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-permission div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class PermissionDialogPage {
    modalTitle = element(by.css('h4#myPermissionLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    extentionInput = element(by.css('input#field_extention'));
    descriptionInput = element(by.css('input#field_description'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    }

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
    }

    setExtentionInput = function(extention) {
        this.extentionInput.sendKeys(extention);
    }

    getExtentionInput = function() {
        return this.extentionInput.getAttribute('value');
    }

    setDescriptionInput = function(description) {
        this.descriptionInput.sendKeys(description);
    }

    getDescriptionInput = function() {
        return this.descriptionInput.getAttribute('value');
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
