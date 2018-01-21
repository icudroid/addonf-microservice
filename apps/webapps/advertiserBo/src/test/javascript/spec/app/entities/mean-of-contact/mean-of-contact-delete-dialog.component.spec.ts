/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AdvertiserBoTestModule } from '../../../test.module';
import { MeanOfContactDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/mean-of-contact/mean-of-contact-delete-dialog.component';
import { MeanOfContactService } from '../../../../../../main/webapp/app/entities/mean-of-contact/mean-of-contact.service';

describe('Component Tests', () => {

    describe('MeanOfContact Management Delete Component', () => {
        let comp: MeanOfContactDeleteDialogComponent;
        let fixture: ComponentFixture<MeanOfContactDeleteDialogComponent>;
        let service: MeanOfContactService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdvertiserBoTestModule],
                declarations: [MeanOfContactDeleteDialogComponent],
                providers: [
                    MeanOfContactService
                ]
            })
            .overrideTemplate(MeanOfContactDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MeanOfContactDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MeanOfContactService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
