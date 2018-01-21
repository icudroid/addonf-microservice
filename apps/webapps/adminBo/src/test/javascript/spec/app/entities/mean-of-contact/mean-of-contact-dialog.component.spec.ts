/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AdminBoTestModule } from '../../../test.module';
import { MeanOfContactDialogComponent } from '../../../../../../main/webapp/app/entities/mean-of-contact/mean-of-contact-dialog.component';
import { MeanOfContactService } from '../../../../../../main/webapp/app/entities/mean-of-contact/mean-of-contact.service';
import { MeanOfContact } from '../../../../../../main/webapp/app/entities/mean-of-contact/mean-of-contact.model';
import { ContactService } from '../../../../../../main/webapp/app/entities/contact';

describe('Component Tests', () => {

    describe('MeanOfContact Management Dialog Component', () => {
        let comp: MeanOfContactDialogComponent;
        let fixture: ComponentFixture<MeanOfContactDialogComponent>;
        let service: MeanOfContactService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdminBoTestModule],
                declarations: [MeanOfContactDialogComponent],
                providers: [
                    ContactService,
                    MeanOfContactService
                ]
            })
            .overrideTemplate(MeanOfContactDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MeanOfContactDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MeanOfContactService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new MeanOfContact(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.meanOfContact = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'meanOfContactListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new MeanOfContact();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.meanOfContact = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'meanOfContactListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
