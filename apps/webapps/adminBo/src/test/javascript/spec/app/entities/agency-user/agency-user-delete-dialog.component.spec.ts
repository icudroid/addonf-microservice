/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AdminBoTestModule } from '../../../test.module';
import { AgencyUserDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/agency-user/agency-user-delete-dialog.component';
import { AgencyUserService } from '../../../../../../main/webapp/app/entities/agency-user/agency-user.service';

describe('Component Tests', () => {

    describe('AgencyUser Management Delete Component', () => {
        let comp: AgencyUserDeleteDialogComponent;
        let fixture: ComponentFixture<AgencyUserDeleteDialogComponent>;
        let service: AgencyUserService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdminBoTestModule],
                declarations: [AgencyUserDeleteDialogComponent],
                providers: [
                    AgencyUserService
                ]
            })
            .overrideTemplate(AgencyUserDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AgencyUserDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AgencyUserService);
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
