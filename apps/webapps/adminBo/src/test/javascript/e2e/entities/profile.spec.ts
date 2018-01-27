import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Profile e2e test', () => {

    let navBarPage: NavBarPage;
    let profileDialogPage: ProfileDialogPage;
    let profileComponentsPage: ProfileComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Profiles', () => {
        navBarPage.goToEntity('profile');
        profileComponentsPage = new ProfileComponentsPage();
        expect(profileComponentsPage.getTitle()).toMatch(/adminBoApp.profile.home.title/);

    });

    it('should load create Profile dialog', () => {
        profileComponentsPage.clickOnCreateButton();
        profileDialogPage = new ProfileDialogPage();
        expect(profileDialogPage.getModalTitle()).toMatch(/adminBoApp.profile.home.createOrEditLabel/);
        profileDialogPage.close();
    });

    it('should create and save Profiles', () => {
        profileComponentsPage.clickOnCreateButton();
        profileDialogPage.setNameInput('name');
        expect(profileDialogPage.getNameInput()).toMatch('name');
        profileDialogPage.setDescriptionInput('description');
        expect(profileDialogPage.getDescriptionInput()).toMatch('description');
        // profileDialogPage.rolesSelectLastOption();
        profileDialogPage.save();
        expect(profileDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ProfileComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-profile div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ProfileDialogPage {
    modalTitle = element(by.css('h4#myProfileLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    descriptionInput = element(by.css('input#field_description'));
    rolesSelect = element(by.css('select#field_roles'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    }

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
    }

    setDescriptionInput = function(description) {
        this.descriptionInput.sendKeys(description);
    }

    getDescriptionInput = function() {
        return this.descriptionInput.getAttribute('value');
    }

    rolesSelectLastOption = function() {
        this.rolesSelect.all(by.tagName('option')).last().click();
    }

    rolesSelectOption = function(option) {
        this.rolesSelect.sendKeys(option);
    }

    getRolesSelect = function() {
        return this.rolesSelect;
    }

    getRolesSelectedOption = function() {
        return this.rolesSelect.element(by.css('option:checked')).getText();
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
