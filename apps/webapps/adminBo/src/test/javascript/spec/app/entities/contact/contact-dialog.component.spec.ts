/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AdminBoTestModule } from '../../../test.module';
import { ContactDialogComponent } from '../../../../../../main/webapp/app/entities/contact/contact-dialog.component';
import { ContactService } from '../../../../../../main/webapp/app/entities/contact/contact.service';
import { Contact } from '../../../../../../main/webapp/app/entities/contact/contact.model';
import { BrandService } from '../../../../../../main/webapp/app/entities/brand';
import { AgencyService } from '../../../../../../main/webapp/app/entities/agency';
import { MediaService } from '../../../../../../main/webapp/app/entities/media';

describe('Component Tests', () => {

    describe('Contact Management Dialog Component', () => {
        let comp: ContactDialogComponent;
        let fixture: ComponentFixture<ContactDialogComponent>;
        let service: ContactService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdminBoTestModule],
                declarations: [ContactDialogComponent],
                providers: [
                    BrandService,
                    AgencyService,
                    MediaService,
                    ContactService
                ]
            })
            .overrideTemplate(ContactDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ContactDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContactService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Contact(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.contact = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'contactListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Contact();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.contact = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'contactListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});