import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('MediaUser e2e test', () => {

    let navBarPage: NavBarPage;
    let mediaUserDialogPage: MediaUserDialogPage;
    let mediaUserComponentsPage: MediaUserComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load MediaUsers', () => {
        navBarPage.goToEntity('media-user');
        mediaUserComponentsPage = new MediaUserComponentsPage();
        expect(mediaUserComponentsPage.getTitle()).toMatch(/adminBoApp.mediaUser.home.title/);

    });

    it('should load create MediaUser dialog', () => {
        mediaUserComponentsPage.clickOnCreateButton();
        mediaUserDialogPage = new MediaUserDialogPage();
        expect(mediaUserDialogPage.getModalTitle()).toMatch(/adminBoApp.mediaUser.home.createOrEditLabel/);
        mediaUserDialogPage.close();
    });

    it('should create and save MediaUsers', () => {
        mediaUserComponentsPage.clickOnCreateButton();
        mediaUserDialogPage.setUserIdInput('5');
        expect(mediaUserDialogPage.getUserIdInput()).toMatch('5');
        mediaUserDialogPage.mediaSelectLastOption();
        mediaUserDialogPage.save();
        expect(mediaUserDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class MediaUserComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-media-user div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class MediaUserDialogPage {
    modalTitle = element(by.css('h4#myMediaUserLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    userIdInput = element(by.css('input#field_userId'));
    mediaSelect = element(by.css('select#field_media'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setUserIdInput = function(userId) {
        this.userIdInput.sendKeys(userId);
    }

    getUserIdInput = function() {
        return this.userIdInput.getAttribute('value');
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
