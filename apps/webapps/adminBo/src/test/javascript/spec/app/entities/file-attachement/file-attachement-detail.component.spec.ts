/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { AdminBoTestModule } from '../../../test.module';
import { FileAttachementDetailComponent } from '../../../../../../main/webapp/app/entities/file-attachement/file-attachement-detail.component';
import { FileAttachementService } from '../../../../../../main/webapp/app/entities/file-attachement/file-attachement.service';
import { FileAttachement } from '../../../../../../main/webapp/app/entities/file-attachement/file-attachement.model';

describe('Component Tests', () => {

    describe('FileAttachement Management Detail Component', () => {
        let comp: FileAttachementDetailComponent;
        let fixture: ComponentFixture<FileAttachementDetailComponent>;
        let service: FileAttachementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdminBoTestModule],
                declarations: [FileAttachementDetailComponent],
                providers: [
                    FileAttachementService
                ]
            })
            .overrideTemplate(FileAttachementDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FileAttachementDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FileAttachementService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new FileAttachement(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.fileAttachement).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
