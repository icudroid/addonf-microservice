import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('MediaTarget e2e test', () => {

    let navBarPage: NavBarPage;
    let mediaTargetDialogPage: MediaTargetDialogPage;
    let mediaTargetComponentsPage: MediaTargetComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load MediaTargets', () => {
        navBarPage.goToEntity('media-target');
        mediaTargetComponentsPage = new MediaTargetComponentsPage();
        expect(mediaTargetComponentsPage.getTitle()).toMatch(/advertiserBoApp.mediaTarget.home.title/);

    });

    it('should load create MediaTarget dialog', () => {
        mediaTargetComponentsPage.clickOnCreateButton();
        mediaTargetDialogPage = new MediaTargetDialogPage();
        expect(mediaTargetDialogPage.getModalTitle()).toMatch(/advertiserBoApp.mediaTarget.home.createOrEditLabel/);
        mediaTargetDialogPage.close();
    });

    it('should create and save MediaTargets', () => {
        mediaTargetComponentsPage.clickOnCreateButton();
        mediaTargetDialogPage.mediaTypeSelectLastOption();
        mediaTargetDialogPage.brandSelectLastOption();
        mediaTargetDialogPage.save();
        expect(mediaTargetDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class MediaTargetComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-media-target div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class MediaTargetDialogPage {
    modalTitle = element(by.css('h4#myMediaTargetLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    mediaTypeSelect = element(by.css('select#field_mediaType'));
    brandSelect = element(by.css('select#field_brand'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setMediaTypeSelect = function(mediaType) {
        this.mediaTypeSelect.sendKeys(mediaType);
    }

    getMediaTypeSelect = function() {
        return this.mediaTypeSelect.element(by.css('option:checked')).getText();
    }

    mediaTypeSelectLastOption = function() {
        this.mediaTypeSelect.all(by.tagName('option')).last().click();
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
